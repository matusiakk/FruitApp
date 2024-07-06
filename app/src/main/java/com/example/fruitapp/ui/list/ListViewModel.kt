package com.example.fruitapp.ui.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fruitapp.data.FavoriteDao
import com.example.fruitapp.data.FruitApi
import com.example.fruitapp.nav.NavEvent
import com.example.fruitapp.nav.Navigator
import com.example.fruitapp.nav.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val api: FruitApi,
    private val favoriteDao: FavoriteDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(ListState())
    val state = _state.asStateFlow()
    val favorite = savedStateHandle.get<Boolean>("favorite") ?: false

    init {
        getList(favorite)
    }

    private fun getList(favorite: Boolean) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            _state.update {
                it.copy(
                    fruitList = api.getList(),
                    isLoading = false,
                    showFavorite = false
                )
            }
            if (favorite) onFavoriteClick()
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
            is ListIntent.OnFavoriteClick -> onFavoriteClick()
        }
    }

    private fun onFavoriteClick() {
        if (!state.value.showFavorite) {
            viewModelScope.launch(Dispatchers.IO) {
                val favoriteNamesList = favoriteDao.getAllFav()
                val favoriteFruitList = state.value.fruitList.filter {
                    it.name in favoriteNamesList
                }
                _state.update {
                    it.copy(
                        fruitList = favoriteFruitList,
                        showFavorite = true
                    )
                }
            }
        } else getList(false)
    }


    private fun selectOption(option: NutritionOptions) {
        _state.update {
            it.copy(
                nutrition = option,
                isOptionMenuVisible = false,
                sortingOption = 0
            )
        }
    }

    private fun onOptionDismiss() {
        _state.update { it.copy(isOptionMenuVisible = false) }
    }

    private fun onOptionClick() {
        _state.update { it.copy(isOptionMenuVisible = true) }
    }

    private fun onSortingDismiss() {
        _state.update { it.copy(isSortingMenuVisible = false) }
    }

    private fun onSortingClick() {
        _state.update { it.copy(isSortingMenuVisible = true) }
    }

    private fun onDescendingClick() {
        val nutrition = _state.value.nutrition
        val sortedFruitList = _state.value.fruitList.sortedByDescending {
            when (nutrition) {
                NutritionOptions.Calories -> it.nutritions.calories.toDouble()
                NutritionOptions.Carbohydrates -> it.nutritions.carbohydrates
                NutritionOptions.Fat -> it.nutritions.fat
                NutritionOptions.Protein -> it.nutritions.protein
                NutritionOptions.Sugar -> it.nutritions.sugar
            }
        }
        _state.update {
            it.copy(
                fruitList = sortedFruitList,
                isSortingMenuVisible = false,
                sortingOption = 2
            )
        }
    }

    private fun onAscendingClick() {
        val nutrition = _state.value.nutrition
        val sortedFruitList = _state.value.fruitList.sortedBy {
            when (nutrition) {
                NutritionOptions.Calories -> it.nutritions.calories.toDouble()
                NutritionOptions.Carbohydrates -> it.nutritions.carbohydrates
                NutritionOptions.Fat -> it.nutritions.fat
                NutritionOptions.Protein -> it.nutritions.protein
                NutritionOptions.Sugar -> it.nutritions.sugar
            }
        }
        _state.update {
            it.copy(
                fruitList = sortedFruitList,
                isSortingMenuVisible = false,
                sortingOption = 1
            )
        }
    }


    private fun onBackClick() {
        Navigator.sendEvent(NavEvent.NavigateBack)
    }

    private fun onFruitClick(name: String) {
        Navigator.sendEvent(NavEvent.NavigateTo(Screen.DetailsScreen.withArgs(name)))
    }
}
