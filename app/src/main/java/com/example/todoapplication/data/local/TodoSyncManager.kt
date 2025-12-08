package com.example.todoapplication.data.local

import android.content.Context
import com.example.todoapplication.data.model.TodoUpdate
import com.example.todoapplication.data.repository.ToDoRepository
import org.json.JSONArray
import org.json.JSONObject

object TodoSyncManager {

    private const val FILE_NAME = "todo_sync_queue.json"

    /**
     * 将操作写入本地队列
     * type: "insert" | "update" | "delete"
     */
    fun addOperation(context: Context, type: String, todoId: String? = null, updates: TodoUpdate? = null, insertData: JSONObject? = null) {
        val file = context.getFileStreamPath(FILE_NAME)
        val queue = if (file.exists()) {
            JSONArray(file.readText())
        } else {
            JSONArray()
        }

        val obj = JSONObject().apply {
            put("type", type)
            todoId?.let { put("todoId", it) }
            updates?.let { put("updates", JSONObject().apply {
                it.title?.let { t -> put("title", t) }
                it.content?.let { c -> put("content", c) }
                it.deadline?.let { d -> put("deadline", d) }
                it.priority?.let { p -> put("priority", p) }
                it.repeatType?.let { r -> put("repeatType", r) }
                it.classify?.let { c -> put("classify", c) }
                it.status?.let { s -> put("status", s) }
            })}
            insertData?.let { put("insertData", it) }
        }

        queue.put(obj)
        file.writeText(queue.toString())
    }

    /**
     * 同步队列，尝试发送到后端
     */
    suspend fun syncQueue(context: Context, repo: ToDoRepository) {
        val file = context.getFileStreamPath(FILE_NAME)
        if (!file.exists()) return

        val queue = JSONArray(file.readText())
        val remainingQueue = JSONArray()

        for (i in 0 until queue.length()) {
            val obj = queue.getJSONObject(i)
            try {
                when (obj.getString("type")) {
                    "insert" -> {
                        val data = obj.getJSONObject("insertData")
                        repo.insertTodo(
                            com.example.todoapplication.data.api.model.InsertTodoRequest(
                                data.getString("userId"),
                                data.getString("title"),
                                data.getString("content"),
                                data.optString("deadline"),
                                data.optInt("status", 0),
                                data.optInt("priority", 1),
                                data.optInt("repeatType", 0),
                                data.optString("classify", "生活")
                            )
                        )
                    }
                    "update" -> {
                        val todoId = obj.getString("todoId")
                        val updatesJson = obj.getJSONObject("updates")
                        val updates = TodoUpdate(
                            title = updatesJson.optString("title", null),
                            content = updatesJson.optString("content", null),
                            deadline = updatesJson.optString("deadline", null),
                            priority = if (updatesJson.has("priority")) updatesJson.optInt("priority") else null,
                            repeatType = if (updatesJson.has("repeatType")) updatesJson.optInt("repeatType") else null,
                            classify = updatesJson.optString("classify", null),
                            status = if (updatesJson.has("status")) updatesJson.optInt("status") else null
                        )
                        repo.updateTodo(todoId, updates)
                    }
                    "delete" -> {
                        val todoId = obj.getString("todoId")
                        repo.deleteTodo(todoId)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // 失败就加入剩余队列，下次再试
                remainingQueue.put(obj)
            }
        }

        // 写回剩余队列
        if (remainingQueue.length() > 0) {
            file.writeText(remainingQueue.toString())
        } else {
            file.delete()
        }
    }
}