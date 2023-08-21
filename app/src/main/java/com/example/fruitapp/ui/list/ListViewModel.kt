package com.example.fruitapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fruitapp.data.FruitApi
import com.example.fruitapp.nav.NavEvent
import com.example.fruitapp.nav.Navigator
import com.example.fruitapp.nav.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val api: FruitApi
) : ViewModel() {

    private val _state = MutableStateFlow(ListState())
    val state = _state.asStateFlow()

    init {
        getList()
    }

    private fun getList() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            _state.value = _state.value.copy(
                fruitList = api.getList(),
                isLoading = false
            )
        }
    }

    fun onIntent(intent: ListIntent) {
        when (intent) {
            is ListIntent.OnFruitClick -> onFruitClick(intent.name)

        }
    }

    private fun onFruitClick(name: String) {
        Navigator.sendEvent(NavEvent.NavigateTo(Screen.DetailsScreen.withArgs(name)))
    }
}