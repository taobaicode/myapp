package com.example.myapplication.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(input: QueryScreenInput, modifier: Modifier = Modifier, eventHandler: QueryScreenEventHandler = QueryScreenEventHandler.NoOp) {
    Scaffold(
        topBar = {
            LaunchedEffect(input.query) {
                if (input.query.isNotEmpty())
                    eventHandler.onQuery(input.query)
            }
            var value by rememberSaveable(input.query) {mutableStateOf(input.query)}
            TextField(modifier = Modifier.fillMaxWidth(), value = value, onValueChange = {
                value = it
                eventHandler.onQuery(it)})
        }
    ) {
        val lazyColumnState = rememberLazyListState()
        LazyColumn(modifier = modifier
            .padding(it)
            .fillMaxSize(),
            state = lazyColumnState
        ) {
            items(count = input.people.size, key = {index-> input.people[index].uid}) { index->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable { eventHandler.onPeopleClicked(input.people[index]) }) {
                    Text(text = input.people[index].name)
                }
            }
        }
    }
}