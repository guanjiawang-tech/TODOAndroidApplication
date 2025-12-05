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