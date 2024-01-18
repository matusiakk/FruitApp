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
            is ListIntent.OnBackClick -> onBackClick()
            is ListIntent.OnSortingClick -> onSortingClick()
            is ListIntent.OnSortingDismiss -> onSortingDismiss()
            is ListIntent.OnAscendingClick -> onAscendingClick()
            is ListIntent.OnDescendingClick -> onDescendingClick()
            is ListIntent.OnOptionClick -> onOptionClick()
            is ListIntent.OnOptionDismiss -> onOptionDismiss()
            is ListIntent.SelectOption -> selectOption(intent.option)
        }
    }

    private fun selectOption(option: String) {
        _state.value =
            _state.value.copy(nutrition = option, isOptionMenuVisible = false, sortingOption = 0)

    }

    private fun onOptionDismiss() {
        _state.value = _state.value.copy(isOptionMenuVisible = false)
    }

    private fun onOptionClick() {
        _state.value = _state.value.copy(isOptionMenuVisible = true)
    }

    private fun onSortingDismiss() {
        _state.value = _state.value.copy(isSortingMenuVisible = false)
    }

    private fun onSortingClick() {
        _state.value = _state.value.copy(isSortingMenuVisible = true)
    }

    private fun onDescendingClick() {
        val nutrition = _state.value.nutrition
        val sortedFruitList = _state.value.fruitList.sortedByDescending {
            when (nutrition) {
                "Calories" -> it.nutritions.calories.toDouble()
                "Carbohydrates" -> it.nutritions.carbohydrates
                "Fat" -> it.nutritions.fat
                "Protein" -> it.nutritions.protein
                "Sugar" -> it.nutritions.sugar
                else -> throw IllegalArgumentException("Unknown nutrition: $nutrition")
            }
        }
        _state.value = _state.value.copy(
            fruitList = sortedFruitList,
            isSortingMenuVisible = false,
            sortingOption = 2
        )
    }

    private fun onAscendingClick() {
        val nutrition = _state.value.nutrition
        val sortedFruitList = _state.value.fruitList.sortedBy {
            when (nutrition) {
                "Calories" -> it.nutritions.calories.toDouble()
                "Carbohydrates" -> it.nutritions.carbohydrates
                "Fat" -> it.nutritions.fat
                "Protein" -> it.nutritions.protein
                "Sugar" -> it.nutritions.sugar
                else -> throw IllegalArgumentException("Unknown nutrition: $nutrition")
            }
        }
        _state.value = _state.value.copy(
            fruitList = sortedFruitList,
            isSortingMenuVisible = false,
            sortingOption = 1
        )
    }


    private fun onBackClick() {
        Navigator.sendEvent(NavEvent.NavigateBack)
    }

    private fun onFruitClick(name: String) {
        Navigator.sendEvent(NavEvent.NavigateTo(Screen.DetailsScreen.withArgs(name)))
    }
}