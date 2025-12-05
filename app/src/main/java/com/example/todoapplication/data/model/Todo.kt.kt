package com.example.todoapplication.data.model

data class Todo(
    val _id: String,
    val userId: String,
    val title: String,
    val content: String,
    val createdAt: String,
    val deadline: String?,
    val status: Int,
    val priority: Int,
    val repeatType: Int
)