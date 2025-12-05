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

data class TodoUpdate(
    val title: String? = null,
    val content: String? = null,
    val deadline: String? = null,
    val status: Int? = null,
    val priority: Int? = null,
    val repeatType: Int? = null
)