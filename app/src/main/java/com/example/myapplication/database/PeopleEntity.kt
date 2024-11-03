package com.example.myapplication.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "People", indices = [Index("name")])
data class PeopleEntity(
    @PrimaryKey val uid : Int,
    val name : String,
    val height: String,
    val birthYear: String,
    val mass : String,
)
