package com.example.fruitapp.ui.details

import com.example.fruitapp.data.Fruit
import com.example.fruitapp.data.Image

data class DetailsState(
    var fruit: Fruit? = null,
    var isLoading: Boolean = false,
    var fruitImage: Image? = null,
    var imageIsLoading: Boolean = false
)