package com.example.todoapplication.data.api.model


data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val code: Boolean,
    val msg: String,
    val userId: String? = null
)