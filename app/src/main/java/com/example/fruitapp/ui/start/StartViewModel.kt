package com.example.fruitapp.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.fruitapp.data.FruitApi
import com.example.fruitapp.nav.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val api: FruitApi
): ViewModel(){

    lateinit var navController: NavHostController

    private val _state = MutableStateFlow(StartState())
    val state: StateFlow<StartState> = _state

    private val _intent = MutableSharedFlow<StartIntent>()
    val intent = _intent.asSharedFlow()


    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intent.collect { intent ->
                when (intent) {
                    is StartIntent.EnterText -> enterText(intent.text)
                    is StartIntent.OnSearchButtonClick -> onSearchButtonClick()
                    is StartIntent.OnListButtonClick -> onListButtonClick()
                }
            }
        }
    }


    private fun enterText(text: String) {
        viewModelScope.launch {
                 if (text.all {it.isLetter()})
                _state.value = _state.value.copy(text = text)
        }
    }

    private fun onSearchButtonClick() {
         viewModelScope.launch{

          val text =_state.value.text

           if (text.isNotEmpty()) {
               viewModelScope.launch {
                   try {
                       _state.value = state.value.copy(
                           fruit = api.getFruitDetails(text),
                           showMessage = false
                       )
                       navController.navigate(Screen.DetailsScreen.withArgs(_state.value.text))

                   } catch (e: Exception) {
                       _state.value = state.value.copy(
                           fruit = null)
                       _state.value = _state.value.copy(showMessage = true)
                   }
               }
            }
        }
    }

    private fun onListButtonClick() {
        navController.navigate(Screen.ListScreen.route)
    }


    fun setIntent(intent: StartIntent) = viewModelScope.launch { _intent.emit(intent) }
}

