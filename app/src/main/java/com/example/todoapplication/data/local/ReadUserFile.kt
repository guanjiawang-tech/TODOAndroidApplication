/**
 * 读文档
 * */
package com.example.todoapplication.data.local

import android.content.Context
import com.example.todoapplication.data.model.TodoItem
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
            val todosList = mutableListOf<TodoItem>()

            // todos 是字符串形式的数组，需要转换成 List<String>
            if (todosJson != null) {
                for (i in 0 until todosJson.length()) {
                    val todoJson = todosJson.getJSONObject(i)

                    val todo = TodoItem(
                        _id = todoJson.optString("_id"),
                        userId = todoJson.optString("userId"),
                        title = todoJson.optString("title"),
                        content = todoJson.optString("content"),
                        createdAt = todoJson.optString("createdAt"),
                        deadline = todoJson.optString("deadline"),
                        status = todoJson.optInt("status"),
                        priority = todoJson.optInt("priority", 1),
                        repeatType = todoJson.optInt("repeatType"),
                        classify = todoJson.optString("classify")
                    )

                    todosList.add(todo)
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