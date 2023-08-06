package com.example.fruitapp.vm

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fruitapp.data.Fruit
import com.example.fruitapp.data.FruitApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val api: FruitApi
): ViewModel() {

    val state = mutableStateOf<List<Fruit>>(emptyList())

    fun getList(){
        viewModelScope.launch{
            try {
                state.value = api.getList()
            } catch (e: Exception){
                Log.e("VM", "getList: ", e)
            }
        }
    }
}