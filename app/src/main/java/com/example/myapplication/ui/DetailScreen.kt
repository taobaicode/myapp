package com.example.myapplication.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.model.People

@Composable
fun DetailScreen(
    input: PeopleUI?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        if (input != null) {
            Text(text = input.name)
            Text(text = input.birthYear)
            Text(text = input.height)
            Text(text = input.mass)
        }
    }
}


@Preview
@Composable
private fun DetailScreenPreview() {
    DetailScreen(input = PeopleUI(
        uid = 1,
        name = "M B",
        height = "5f9",
        mass = "120",
        birthYear = "2000"
    ))
}
