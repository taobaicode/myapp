package com.example.myapplication.network

import com.example.myapplication.model.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryName

interface ApiService {
    @GET("/api/people")
    suspend fun retrievePeople(@Query("name") name: String): Response
}