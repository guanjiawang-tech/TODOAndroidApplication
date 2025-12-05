package com.example.todoapplication.data.api.model

import com.example.todoapplication.data.model.Todo
import com.example.todoapplication.data.model.TodoUpdate


data class TodoRequest(
    val userId: String? = null
)

data class TodoResponse(
    val code: Boolean,
    val msg: String,
    val data: List<Todo>? = null
)

data class UpdateTodoRequest(
    val todoId: String? = null,
    val updates: TodoUpdate? = null,
)

data class InsertTodoRequest(
    val userId: String,
    val title: String,
    val content: String,
    val deadline: String?,
    val status: Int,
    val priority: Int,
    val repeatType: Int
)