package com.example.fruitapp.ui.list

import com.example.fruitapp.data.Fruit

data class ListState(
    var fruitList: List<Fruit> = emptyList(),
    var nutritionList: List<String> = listOf(
        "Calories",
        "Carbohydrates",
        "Fat",
        "Protein",
        "Sugar"
    ),
    var nutrition: String = "Calories",
    var isLoading: Boolean = false,
    var isSortingMenuVisible: Boolean = false,
    var sortingOption: Int = 0,
    var isOptionMenuVisible: Boolean = false

)