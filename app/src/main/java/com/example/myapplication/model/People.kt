package com.example.myapplication.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import timber.log.Timber

@JsonClass(generateAdapter = true)
data class People(
    @Json(name="uid") val uid : Long,
    @Json(name="properties") val properties: PeopleProperties
) {
    fun toJson() : String =
        Moshi.Builder().build().adapter(People::class.java).toJson(this)

    companion object {
        fun from (json: String) =
            runCatching {
                Moshi.Builder().build().adapter(People::class.java).fromJson(json)
            }.fold(onSuccess = {it}, onFailure = { Timber.e(it)})
    }
}

