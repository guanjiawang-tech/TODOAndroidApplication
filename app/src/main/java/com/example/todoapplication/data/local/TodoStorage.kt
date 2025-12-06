package com.example.todoapplication.data.local

import android.content.Context
import com.example.todoapplication.data.model.Todo
import com.example.todoapplication.data.model.TodoUpdate
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


    /**
     * 修改本地文件
     * */
    private fun updateToMap(update: TodoUpdate): Map<String, Any?> {
        return mapOf(
            "title" to update.title,
            "content" to update.content,
            "deadline" to update.deadline,
            "status" to update.status,
            "priority" to update.priority,
            "repeatType" to update.repeatType
        ).filterValues { it != null }
    }

    fun updateTodo(
        context: Context,
        todoId: String,
        updates: Map<String, Any?>
    ): Boolean {
        val file = File(context.filesDir, FILE_NAME)
        if (!file.exists()) return false

        val json = JSONObject(file.readText())
        val todosArray = json.optJSONArray("todos") ?: return false

        for (i in 0 until todosArray.length()) {
            val item = todosArray.getJSONObject(i)

            if (item.optString("_id") == todoId) {
                updates.forEach { (key, value) ->
                    item.put(key, value ?: JSONObject.NULL)
                }

                json.put("todos", todosArray)
                file.writeText(json.toString())
                return true
            }
        }

        return false
    }

    /**
     * 修改本地文件的便于传参的优化方法
     * @Usage
     *
     * TodoStorage.updateTodo(
     *     context,
     *     todoId = "674147ff2b338f91fef87cb2",
     *     update = TodoUpdate(
     *         status = 1,
     *         priority = 2,
     *         deadline = "2025-12-07T18:00:00Z"
     *     )
     * )
     * */
    fun updateTodo(context: Context, todoId: String, update: TodoUpdate): Boolean {
        return updateTodo(context, todoId, updateToMap(update))
    }


    fun addTodo(
        context: Context,
        todo: Todo
    ): Boolean {
        val file = File(context.filesDir, FILE_NAME)

        // 若文件不存在则不写（保持你的安全策略）
        if (!file.exists()) return false

        // 读取现有 JSON
        val json = JSONObject(file.readText())

        // 获取已有 todos 数组，如不存在则创建
        val todosArray = json.optJSONArray("todos") ?: JSONArray()

        // 转成 JSON 对象
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

        // 追加到数组
        todosArray.put(todoJson)

        // 更新 JSON 根结构
        json.put("todos", todosArray)

        // 写回文件
        file.writeText(json.toString())

        return true
    }

}