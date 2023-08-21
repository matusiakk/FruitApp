package com.example.fruitapp.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fruitapp.data.FruitApi
import com.example.fruitapp.nav.NavEvent
import com.example.fruitapp.nav.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.SavedStateHandle
import com.example.fruitapp.nav.Screen

@HiltViewModel
class StartViewModel @Inject constructor(
    private val api: FruitApi
) : ViewModel() {

    private val _state = MutableStateFlow(StartState())
    val state: StateFlow<StartState> = _state


    fun onIntent(intent: StartIntent) {
        when (intent) {
            is StartIntent.EnterText -> enterText(intent.text)
            is StartIntent.OnSearchButtonClick -> onSearchButtonClick()
            is StartIntent.OnListButtonClick -> onListButtonClick()
        }
    }

    private fun enterText(text: String) {
            if (text.all { it.isLetter() })
                _state.value = _state.value.copy(text = text)
        }

    private fun onSearchButtonClick() {

            val text = _state.value.text

            if (text.isNotEmpty()) {
                viewModelScope.launch {
                    try {
                        _state.value = state.value.copy(
                            fruit = api.getFruitDetails(text),
                            showMessage = false
                        )
                        Navigator.sendEvent(NavEvent.NavigateTo(Screen.DetailsScreen.withArgs(text)))

                    } catch (e: Exception) {
                        _state.value = state.value.copy(
                            fruit = null
                        )
                        _state.value = _state.value.copy(showMessage = true)
                    }
                }
            }
        }

    private fun onListButtonClick() {
        Navigator.sendEvent(NavEvent.NavigateTo(Screen.ListScreen.route))
    }

}

