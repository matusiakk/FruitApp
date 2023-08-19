package com.example.fruitapp.ui.details

import android.util.Log
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
class DetailsViewModel @Inject constructor(
    private val api: FruitApi
): ViewModel() {

    lateinit var navController: NavHostController

    private val _state = MutableStateFlow(DetailsState())
    val state = _state.asStateFlow()

    private val _intent = MutableSharedFlow<DetailsIntent>()
    val intent = _intent.asSharedFlow()

    init {
    handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intent.collect { intent ->
                when (intent) {
                    is DetailsIntent.LoadDetails -> getFruitDetails(intent.name)
                    is DetailsIntent.OnBackButtonClick -> onBackButtonClick()
                }
            }
        }
    }

    private fun getFruitDetails(name: String){
        viewModelScope.launch{
            try {
                _state.value = state.value.copy(
                    fruit = api.getFruitDetails(name))
            } catch (e: Exception){
                Log.e("VM", "getFruitsDetails: ", e)
            }
        }
    }

    private fun onBackButtonClick() {
        navController.navigate(Screen.StartScreen.route)
    }

    fun setIntent(intent: DetailsIntent) = viewModelScope.launch { _intent.emit(intent) }
}
