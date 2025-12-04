/**
 * 读文档
 * */
package com.example.todoapplication.data.local

import android.content.Context
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

data class UserData(val username: String, val todos: List<String>)
fun parseUserFile(context: Context): UserData? {
    val fileContent = readUserFile(context)
    if (fileContent != null) {
        try {
            val json = JSONObject(fileContent)
            val username = json.optString("user")
            val todosString = json.optString("todos")

            // todos 是字符串形式的数组，需要转换成 List<String>
            val todosList = try {
                val jsonArray = JSONArray(todosString)
                List(jsonArray.length()) { i -> jsonArray.getString(i) }
            } catch (e: Exception) {
                emptyList()
            }
            return UserData(username, todosList)
//            println("用户名: $username")
//            println("待办列表: $todosList")

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return null
}