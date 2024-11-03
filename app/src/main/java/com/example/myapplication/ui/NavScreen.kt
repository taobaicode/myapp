package com.example.myapplication.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.model.People

enum class Route {
    Home,
    Detail
}


@Composable
fun MainScreen(modifier: Modifier = Modifier, viewModel: PeopleViewModel) {

   val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(viewModel.snackbarInputFlow) {
        viewModel.snackbarInputFlow.collect {
            snackbarHostState.showSnackbar(it.message, duration = SnackbarDuration.Short)
        }
    }
    Scaffold(
    ) {
        Box(contentAlignment = Alignment.TopCenter) {
            Box(modifier = Modifier.fillMaxWidth().zIndex(10f)) {
                SnackbarHost(hostState = snackbarHostState)
            }
            val navController = rememberNavController()
            NavHost(
                navController = navController, startDestination = Route.Home.name,
                modifier = modifier.padding(it)
            ) {
                composable(Route.Home.name) {
                    val peopleState = viewModel.people.collectAsState(initial = emptyList()).value
                    val queryState = viewModel.query
                    HomeScreen(
                        QueryScreenInput(people = peopleState, query = queryState ?: ""),
                        eventHandler = object : QueryScreenEventHandler {
                            override fun onQuery(query: String) {
                                viewModel.eventHandler.onQuery(query)
                            }

                            override fun onPeopleClicked(people: PeopleUI) {
                                viewModel.eventHandler.onPeopleClicked(people)
                                navController.navigate(Route.Detail.name)
                            }
                        })
                }
                composable(Route.Detail.name) {
                    DetailScreen(input = viewModel.curPeople)
                }
            }
        }
    }
}