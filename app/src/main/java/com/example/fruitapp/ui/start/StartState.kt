package com.example.fruitapp.ui.start

import com.example.fruitapp.data.Fruit

data class StartState(
    val text: String = "",
    val fruit: Fruit? = null,
    val showMessage: Boolean = false
)