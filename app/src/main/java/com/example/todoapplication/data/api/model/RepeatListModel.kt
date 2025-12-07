package com.example.todoapplication.data.api.model

import com.example.todoapplication.data.model.RepeatListItem

data class RepeatListResponse(
    val code: Boolean,
    val msg: String,
    val data: List<RepeatListItem> = emptyList()
)