package com.example.myapplication.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PeopleProperties(
    val name: String,
    val height: String,
    @Json(name="birth_year") val birthYear: String,
    val mass : String,
)
