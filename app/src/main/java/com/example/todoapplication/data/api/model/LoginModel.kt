package com.example.todoapplication.data.api.model


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