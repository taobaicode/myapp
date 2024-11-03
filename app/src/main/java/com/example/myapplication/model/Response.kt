package com.example.myapplication.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response(
    val message: String,
    val result: List<People>?
)
