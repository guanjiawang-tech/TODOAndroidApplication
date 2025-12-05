package com.example.todoapplication.data.api

import com.example.todoapplication.data.api.model.LoginRequest
import com.example.todoapplication.data.api.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST("api/loginAndRegister")
    suspend fun loginAndRegister(@Body request: LoginRequest): LoginResponse
}
