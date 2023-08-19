package com.example.fruitapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.fruitapp.data.FruitApi
import com.example.fruitapp.nav.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val api: FruitApi
): ViewModel() {

    lateinit var navController: NavHostController

    private val _state = MutableStateFlow(ListState())
    val state = _state.asStateFlow()

    private val _intent = MutableSharedFlow<ListIntent>()
    val intent = _intent.asSharedFlow()

    init {
        getList()
        handleIntent()
    }

    private fun getList() {
        viewModelScope.launch {
            _state.value = _state.value.copy(fruitList = api.getList())
        }
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intent.collect { intent ->
                when (intent) {
                    is ListIntent.OnFruitClick -> onFruitClick(intent.name)

                }
            }
        }
    }

    private fun onFruitClick(name: String) {
        navController.navigate(Screen.DetailsScreen.withArgs(name))
    }

    fun setIntent(intent: ListIntent) = viewModelScope.launch { _intent.emit(intent) }
}