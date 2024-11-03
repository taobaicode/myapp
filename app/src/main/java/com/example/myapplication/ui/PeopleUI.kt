package com.example.myapplication.ui

import com.example.myapplication.database.PeopleEntity

data class PeopleUI(
    val uid : Int,
    val name : String,
    val height: String,
    val birthYear: String,
    val mass : String,
) {
    companion object {
        fun from(peopleEntity: PeopleEntity) : PeopleUI =
            PeopleUI(
                uid = peopleEntity.uid,
                name = peopleEntity.name,
                height = peopleEntity.height,
                birthYear = peopleEntity.birthYear,
                mass = peopleEntity.mass
            )
    }
}
