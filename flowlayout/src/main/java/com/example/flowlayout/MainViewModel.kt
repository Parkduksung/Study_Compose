package com.example.flowlayout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*

class MainViewModel : ViewModel() {

    private val list = mutableListOf<String>()


    private val mutableState = MutableStateFlow(State())
    val state: StateFlow<State> =
        mutableState.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), State())


    fun add(string: String) {
        list.add(string)
    }

    fun remove(index: Int) {
        list.removeAt(index)
    }

    fun onChanged() {
        mutableState.update { state ->
            state.copy(list = list)
        }
    }

}

data class State(
    val list: List<String> = emptyList()
)