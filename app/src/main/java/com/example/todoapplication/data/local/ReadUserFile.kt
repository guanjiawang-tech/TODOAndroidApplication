/**
 * 读文档
 * */
package com.example.todoapplication.data.local

import android.content.Context
import com.example.todoapplication.data.model.UserData
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

fun readUserFile(context: Context): String? {
    val file = File(context.filesDir, "data.json")
    return if (file.exists()) {
        file.readText()
    } else {
        null
    }
}


fun parseUserFile(context: Context): UserData? {
    val fileContent = readUserFile(context)
    if (fileContent != null) {
        try {
            val json = JSONObject(fileContent)
            val id  = json.optString("_id")
            val username = json.optString("user")
            val todosJson = json.optJSONArray("todos")
            val todosList = mutableListOf<String>()

            // todos 是字符串形式的数组，需要转换成 List<String>
            if (todosJson != null) {
                for (i in 0 until todosJson.length()) {
                    todosList.add(todosJson.getString(i))
                }
            }
            return UserData(id, username, todosList)
//            println("用户名: $username")
//            println("待办列表: $todosList")

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return null
}