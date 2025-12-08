package com.example.todoapplication.data.api.model

import com.example.todoapplication.data.model.RepeatListItem

data class RepeatListResponse(
    val code: Boolean,
    val msg: String,
    val data: List<RepeatListItem> = emptyList()
)


data class repeatListRequest(
    val todoId: String,
    val userId: String,
    val date: String
)

data class RepeatDateResponse(
    val code: Boolean,
    val msg: String,
)


