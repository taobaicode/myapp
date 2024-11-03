package com.example.myapplication.ui


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot.Companion.withMutableSnapshot
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.example.myapplication.repository.PeopleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val peopleRepository: PeopleRepository,
) :ViewModel() {
    private val _people =  MutableStateFlow<List<PeopleUI>>(emptyList())
    val people : StateFlow<List<PeopleUI>> = _people.asStateFlow()

    private val _snackbarInputFlow = MutableSharedFlow<SnackbarInput>()
    val snackbarInputFlow = _snackbarInputFlow.asSharedFlow()

    var query by mutableStateOf<String?>(null)
        private set

    val eventHandler = object : QueryScreenEventHandler {
        override fun onQuery(query: String) {
            query(query)
        }

        override fun onPeopleClicked(people: PeopleUI) {
            curPeople = people
        }
    }

    var curPeople by mutableStateOf<PeopleUI?>(null)
        private set

    private var queryJob : Job? = null
    fun query(name: String) {
        viewModelScope.launch {
            queryJob?.cancel()
            coroutineScope {
                launch {
                    peopleRepository.queryPeople(name)
                }
                queryJob = launch {
                    peopleRepository.getPeople(name).collect {peopleEntities ->
                        _people.emit(peopleEntities.map {PeopleUI.from(it)})
                    }
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            query = peopleRepository.query.first()
        }
        viewModelScope.launch {
            while (true) {
                _snackbarInputFlow.emit(SnackbarInput("Current query $query", 0))
                delay(60000)
            }
        }
    }
}