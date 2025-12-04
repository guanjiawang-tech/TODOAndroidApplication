/**
 * 写文档
 * */
package com.example.todoapplication.data.local

import android.content.Context
import org.json.JSONObject
import java.io.File

object UserStorage {

    private const val FILE_NAME = "data.json"
    private const val TEMP_USER = "guest"

    fun initStorage(context: Context) {
        val file = File(context.filesDir, FILE_NAME)

        if (!file.exists()) {
            // 创建并写入默认 JSON 文件
            val defaultData = JSONObject().apply {
                put("user", JSONObject.NULL)
                put("todos", emptyList<String>())
            }
            file.writeText(defaultData.toString())
        }
    }

    fun getUser(context: Context): String? {
        val file = File(context.filesDir, FILE_NAME)
        if (!file.exists()) return null

        val json = JSONObject(file.readText())
        return if (json.isNull("user")) null else json.getString("user")
    }

    fun saveUser(context: Context, username: String) {
        val file = File(context.filesDir, FILE_NAME)
        val json = JSONObject(file.readText())
        json.put("user", username)
        file.writeText(json.toString())
    }

    fun clearUser(context: Context) {
        val file = File(context.filesDir, FILE_NAME)
        if (file.exists()) {
            val defaultData = JSONObject().apply {
                put("user", JSONObject.NULL)
                put("todos", emptyList<String>())
            }
            file.writeText(defaultData.toString())
        }
    }
}