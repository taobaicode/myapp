package com.example.myapplication.ui

interface QueryScreenEventHandler {
    fun onQuery(query: String)
    fun onPeopleClicked(people: PeopleUI)
    interface Default : QueryScreenEventHandler{
        override fun onQuery(query: String) = Unit
        override fun onPeopleClicked(people: PeopleUI) =Unit
    }
    object NoOp: Default
}