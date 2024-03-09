package com.example.fruitapp.ui.details

import android.content.Intent
import android.net.Uri
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
import com.example.fruitapp.data.ImageApi
import kotlinx.coroutines.flow.update

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val api: FruitApi,
    private val imageApi: ImageApi
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsState())
    val state = _state.asStateFlow()

    init {
        getFruitDetails(requireNotNull(savedStateHandle.get<String>("name")))
        getFruitImage(requireNotNull(savedStateHandle.get<String>("name")))
    }

    fun onIntent(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.OnBackButtonClick -> onBackButtonClick()
            is DetailsIntent.OnPexelsClick -> onPexelsClick()
        }
    }

    private fun onPexelsClick() {
        Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pexels.com/"))
    }


    private fun getFruitDetails(name: String) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        fruit = api.getFruitDetails(name)
                    )
                }
            } catch (e: Exception) {
                Log.e("VM", "getFruitsDetails: ", e)
            }
        }
        _state.update { it.copy(isLoading = false) }
    }

    private fun getFruitImage(name: String) {
        _state.update { it.copy(imageIsLoading = true) }
        viewModelScope.launch {
            try {
                _state.update { it.copy(fruitImage = imageApi.getImage(name)) }
                Log.e("IMG", _state.value.fruitImage.toString())
            } catch (e: Exception) {
                Log.e("VM", "getImage", e)
            }
        }
        _state.update { it.copy(imageIsLoading = false) }
    }

    private fun onBackButtonClick() {
        Navigator.sendEvent(NavEvent.NavigateBack)
    }
}
