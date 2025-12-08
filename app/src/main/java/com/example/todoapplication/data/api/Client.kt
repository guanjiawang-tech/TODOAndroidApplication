/**
 * network request client
 * */
package com.example.todoapplication.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {

    private const val BASE_URL = "http://192.168.1.104:3000/"

//    private const val BASE_URL = "http://192.168.1.103:3000/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}