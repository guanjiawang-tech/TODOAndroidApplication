package com.example.todoapplication.data.repository

import com.example.todoapplication.data.api.Client
import com.example.todoapplication.data.api.model.TodoRequest
import com.example.todoapplication.data.api.model.TodoResponse

class ToDoRepository {

    suspend fun GetTodoList(userId: String): TodoResponse? {
        return try {
            Client.apiService.getTodoList(TodoRequest(userId))
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}