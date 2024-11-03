package com.example.myapplication.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PeopleDao {
    @Query("SELECT * FROM People WHERE name LIKE :nameQuery")
    fun getPeople(nameQuery: String) : Flow<List<PeopleEntity>>

    @Upsert
    suspend fun upsert(vararg people : PeopleEntity)
}