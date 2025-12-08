package com.example.todoapplication.data.local

import android.content.Context
import com.example.todoapplication.data.api.model.RepeatListResponse
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

object RepeatListStorage {

    private const val FILE_NAME = "repeatList.json"

    /**
     * 初始化本地存储（若不存在则创建）
     */
    fun init(context: Context) {
        val file = File(context.filesDir, FILE_NAME)
        if (!file.exists()) {
            val defaultJson = JSONObject().apply {
                put("repeatList", JSONArray())
            }
            file.writeText(defaultJson.toString())
        }
    }

    /**
     * 保存 RepeatList 数据到本地
     */
    fun save(context: Context, data: RepeatListResponse) {
        val file = File(context.filesDir, FILE_NAME)

        val resultArray = JSONArray()

        data.data?.forEach { item ->
            val obj = JSONObject().apply {
                put("_id", item._id)
                put("todoId", item.todoId)
                put("date", JSONArray(item.date))
            }
            resultArray.put(obj)
        }

        val jsonRoot = JSONObject().apply {
            put("repeatList", resultArray)
        }

        file.writeText(jsonRoot.toString())
    }

    /**
     * 读取 RepeatList 本地数据
     */
    fun load(context: Context): JSONArray? {
        val file = File(context.filesDir, FILE_NAME)
        if (!file.exists()) return null

        val json = JSONObject(file.readText())
        return json.optJSONArray("repeatList")
    }

    /**
     * 删除文件（登出时可用）
     */
    fun clear(context: Context) {
        val file = File(context.filesDir, FILE_NAME)
        if (file.exists()) file.delete()
    }

    fun getCompletedDates(context: Context, todoId: String): List<String> {
        val repeatList = RepeatListStorage.load(context) ?: return emptyList()
        for (i in 0 until repeatList.length()) {
            val obj = repeatList.getJSONObject(i)
            if (obj.getString("todoId") == todoId) {
                val dateArray = obj.getJSONArray("date")
                return List(dateArray.length()) { dateArray.getString(it) }
            }
        }
        return emptyList()
    }

    fun updateCompletedDate(context: Context, todoId: String, date: String, checked: Boolean) {
        val repeatList = RepeatListStorage.load(context) ?: JSONArray()
        var found = false
        for (i in 0 until repeatList.length()) {
            val obj = repeatList.getJSONObject(i)
            if (obj.getString("todoId") == todoId) {
                found = true
                val dateArray = obj.getJSONArray("date")
                if (checked) {
                    if (!List(dateArray.length()) { dateArray.getString(it) }.contains(date)) {
                        dateArray.put(date)
                    }
                } else {
                    val newArray = JSONArray()
                    for (j in 0 until dateArray.length()) {
                        val d = dateArray.getString(j)
                        if (d != date) newArray.put(d)
                    }
                    obj.put("date", newArray)
                }
                break
            }
        }
        if (!found && checked) {
            val obj = JSONObject()
            obj.put("todoId", todoId)
            obj.put("_id", System.currentTimeMillis().toString())
            obj.put("date", JSONArray().put(date))
            repeatList.put(obj)
        }
        // 保存回文件
        val root = JSONObject().apply { put("repeatList", repeatList) }
        val file = File(context.filesDir, "repeatList.json")
        file.writeText(root.toString())
    }
}