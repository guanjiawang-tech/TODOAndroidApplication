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
}