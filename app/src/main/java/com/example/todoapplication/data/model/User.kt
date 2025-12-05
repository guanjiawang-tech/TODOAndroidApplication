package com.example.todoapplication.data.model

import com.google.android.gms.gcm.Task

data class UserData(
    val id: String,
    val username: String,
    val todos: List<Todo>
)