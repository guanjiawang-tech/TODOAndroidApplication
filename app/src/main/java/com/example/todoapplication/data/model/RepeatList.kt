package com.example.todoapplication.data.model

data class RepeatListItem(
    val _id: String,
    val todoId: String,
    val userId: String,
    val date: List<String>,
    )