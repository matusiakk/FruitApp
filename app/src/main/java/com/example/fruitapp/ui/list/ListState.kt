package com.example.fruitapp.ui.list

import com.example.fruitapp.data.Fruit

data class ListState(
    var fruitList: List<Fruit> = emptyList(),
    var nutrition: NutritionOptions = NutritionOptions.Calories,
    var isLoading: Boolean = false,
    var isSortingMenuVisible: Boolean = false,
    var sortingOption: Int = 0,
    var isOptionMenuVisible: Boolean = false

)

