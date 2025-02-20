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
import com.example.fruitapp.data.Favorite
import com.example.fruitapp.data.FavoriteDao
import com.example.fruitapp.data.ImageApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val api: FruitApi,
    private val imageApi: ImageApi,
    private val favoriteDao: FavoriteDao
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
            is DetailsIntent.OnFavoriteClick -> onFavoriteClick(intent.name)
        }
    }

    private fun onFavoriteClick(name: String) {
        val favorite = Favorite(fruitName = name)
        if (!state.value.isFav) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    favoriteDao.insertFav(favorite)
                    _state.update { it.copy(isFav = true) }
                } catch (e: Exception) {
                    Log.e("VM", "insertFav", e)
                }
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    favoriteDao.deleteFav(name)
                    _state.update { it.copy(isFav = false) }
                } catch (e: Exception) {
                    Log.e("VM", "deleteFav", e)
                }
            }
        }

    }


    private fun getFruitDetails(name: String) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val isFav = favoriteDao.getFavByName(name) != null
                _state.update {
                    it.copy(
                        fruit = api.getFruitDetails(name),
                        isFav = isFav
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
