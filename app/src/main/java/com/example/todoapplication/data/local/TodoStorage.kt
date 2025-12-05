package com.example.todoapplication.data.local

import android.content.Context
import com.example.todoapplication.data.model.Todo
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

object TodoStorage {

    private const val FILE_NAME = "data.json"

    fun saveTodo(context: Context, todos: List<Todo>) {
        val file = File(context.filesDir, FILE_NAME)

        // 如果文件不存在就不写，避免空文件破坏结构
        if (!file.exists()) return

        // 读取现有 JSON
        val json = JSONObject(file.readText())

        // 新的 todos JSON 数组
        val todosArray = JSONArray().apply {
            todos.forEach { todo ->
                val todoJson = JSONObject().apply {
                    put("_id", todo._id)
                    put("userId", todo.userId)
                    put("title", todo.title)
                    put("content", todo.content)
                    put("createdAt", todo.createdAt)
                    put("deadline", todo.deadline ?: JSONObject.NULL)
                    put("status", todo.status)
                    put("priority", todo.priority)
                    put("repeatType", todo.repeatType)
                }
                put(todoJson)
            }
        }

        // 更新现有结构中的 todos 字段
        json.put("todos", todosArray)

        // 写回文件
        file.writeText(json.toString())
    }
}