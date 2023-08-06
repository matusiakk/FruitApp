package com.example.fruitapp.vm

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fruitapp.data.Fruit
import com.example.fruitapp.data.FruitApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val api: FruitApi
): ViewModel() {

    private val _state = mutableStateOf(FruitState())
    val state: State<FruitState> = _state

    fun getFruitDetails(name: String){
        viewModelScope.launch{
            try {
                _state.value = state.value.copy(
                    fruit = api.getFruitDetails(name))
            } catch (e: Exception){
                Log.e("VM", "getFruitsDetails: ", e)
            }
        }
    }

    data class FruitState(
        val fruit: Fruit? = null
    )
}