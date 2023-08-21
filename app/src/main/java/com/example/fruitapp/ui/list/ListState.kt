package com.example.fruitapp.ui.list

import com.example.fruitapp.data.Fruit

data class ListState(
    var fruitList: List<Fruit> = emptyList(),
    var isLoading: Boolean = false
)