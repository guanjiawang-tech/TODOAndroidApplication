package com.example.todoapplication.data.api

import com.example.todoapplication.data.api.model.LoginRequest
import com.example.todoapplication.data.api.model.LoginResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST("api/loginAndRegister")
    suspend fun loginAndRegister(@Body request: LoginRequest): LoginResponse
}

suspend fun login(username: String, password: String): LoginResponse? {
    return try {
        Client.apiService.loginAndRegister(LoginRequest(username, password))
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}