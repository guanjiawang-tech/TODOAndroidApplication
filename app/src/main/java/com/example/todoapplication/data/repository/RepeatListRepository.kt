package com.example.todoapplication.data.repository

import com.example.todoapplication.data.api.Client
import com.example.todoapplication.data.api.model.RepeatDateResponse
import com.example.todoapplication.data.api.model.RepeatListResponse
import com.example.todoapplication.data.api.model.TodoRequest
import com.example.todoapplication.data.api.model.TodoResponse
import com.example.todoapplication.data.api.model.repeatListRequest

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

    /**
     * 更新重复 to do 数据
     */
    suspend fun repeatList(
        todoId: String,
        userId: String,
        date: String,
        ): RepeatDateResponse? {
        return try {
            Client.apiService.repeatList(repeatListRequest(todoId,userId,date))
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}