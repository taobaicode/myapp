package com.example.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        PeopleEntity::class
    ],
    version = 1
    )
abstract class PeopleDatabase : RoomDatabase() {
    abstract fun peopleDao() : PeopleDao
}