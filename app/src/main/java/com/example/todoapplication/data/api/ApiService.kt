package com.example.todoapplication.data.api

import com.example.todoapplication.data.api.model.LoginRequest
import com.example.todoapplication.data.api.model.LoginResponse
import com.example.todoapplication.data.api.model.TodoRequest
import com.example.todoapplication.data.api.model.TodoResponse
import com.example.todoapplication.data.api.model.UpdateTodoRequest
import com.example.todoapplication.data.model.TodoUpdate
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST


interface ApiService {
    /**
     * 登录 / 注册接口
     */
    @POST("api/loginAndRegister")
    suspend fun loginAndRegister(@Body request: LoginRequest): LoginResponse

    /**
     * 获取用户 To do 列表
     */
    @POST("/api/getTodoList")
    suspend fun getTodoList(
        @Body request: TodoRequest
    ): TodoResponse

    /**
     * 更新用户 To do 列表
     */
    @PATCH("/api/updateTodo")
    suspend fun updateTodo(
        @Body request: UpdateTodoRequest
    ): TodoResponse

}
