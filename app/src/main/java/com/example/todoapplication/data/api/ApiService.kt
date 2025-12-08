package com.example.todoapplication.data.api

import com.example.todoapplication.data.api.model.DeleteResponse
import com.example.todoapplication.data.api.model.DeleteTodoRequest
import com.example.todoapplication.data.api.model.InsertTodoRequest
import com.example.todoapplication.data.api.model.LoginRequest
import com.example.todoapplication.data.api.model.LoginResponse
import com.example.todoapplication.data.api.model.RepeatDateResponse
import com.example.todoapplication.data.api.model.RepeatListResponse
import com.example.todoapplication.data.api.model.TodoRequest
import com.example.todoapplication.data.api.model.TodoResponse
import com.example.todoapplication.data.api.model.TodoResponseByOne
import com.example.todoapplication.data.api.model.UpdateTodoRequest
import com.example.todoapplication.data.api.model.repeatListRequest
import com.example.todoapplication.data.model.TodoUpdate
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query


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

    /**
     * 插入用户 To do 列表
     */
    @POST("/api/insertTodo")
    suspend fun insertTodo(
        @Body request: InsertTodoRequest
    ): TodoResponseByOne

    /**
     * 插入用户 To do 列表
     */
    @POST("/api/deleteTodo")
    suspend fun deleteTodo(
        @Body request: DeleteTodoRequest
    ): DeleteResponse

    /**
     * 获取全部重复 to do 数据
     * */
    @GET("/getAllRepeatList")
    suspend fun getAllRepeatList(
        @Query("userId") userId: String
    ): RepeatListResponse

    /**
     * 更新重复 to do 数据
     * */
    @POST("/repeatList")
    suspend fun repeatList(
        @Body request: repeatListRequest
    ): RepeatDateResponse
}
