package com.example.todoapplication.data.repository

import com.example.todoapplication.data.api.Client
import com.example.todoapplication.data.api.model.RepeatListResponse
import com.example.todoapplication.data.api.model.TodoRequest
import com.example.todoapplication.data.api.model.TodoResponse

class RepeatListRepository {

    /**
     * 获取用户 Repeat List 列表
     */
    suspend fun getAllRepeatList(userId: String): RepeatListResponse? {
        return try {
            Client.apiService.getAllRepeatList(userId)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}