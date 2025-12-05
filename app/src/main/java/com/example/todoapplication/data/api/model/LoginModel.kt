package com.example.todoapplication.data.api.model

import com.example.todoapplication.data.model.Todo


data class LoginRequest(
    val username: String,
    val password: String
)

data class UserResponse(
    val username: String? = null,
    val userId: String? = null
)

data class LoginResponse(
    val code: Boolean,
    val msg: String,
    val data: UserResponse? = null
)

data class TodoResponse(
    val code: Boolean,
    val msg: String,
    val data: List<Todo>?
)