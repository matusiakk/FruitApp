package com.example.fruitapp.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fruitapp.data.FruitApi
import com.example.fruitapp.nav.NavEvent
import com.example.fruitapp.nav.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.SavedStateHandle

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val api: FruitApi
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsState())
    val state = _state.asStateFlow()

    init {
        getFruitDetails(requireNotNull(savedStateHandle.get<String>("name")))
    }

    fun onIntent(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.OnBackButtonClick -> onBackButtonClick()
        }
    }


    private fun getFruitDetails(name: String) {
        viewModelScope.launch {
            try {
                _state.value = state.value.copy(
                    fruit = api.getFruitDetails(name)
                )
            } catch (e: Exception) {
                Log.e("VM", "getFruitsDetails: ", e)
            }
        }
    }

    private fun onBackButtonClick() {
        Navigator.sendEvent(NavEvent.NavigateBack)
    }
}
