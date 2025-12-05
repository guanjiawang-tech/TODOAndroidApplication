package com.example.todoapplication.data.repository

import com.example.todoapplication.data.api.Client
import com.example.todoapplication.data.api.model.LoginRequest
import com.example.todoapplication.data.api.model.LoginResponse

class UserRepository {

    suspend fun Login(username: String, password: String): LoginResponse? {
        return try {
            Client.apiService.loginAndRegister(LoginRequest(username, password))
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}