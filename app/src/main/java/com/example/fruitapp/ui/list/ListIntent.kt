package com.example.fruitapp.ui.list

sealed class ListIntent {
    data class OnFruitClick(var name: String) : ListIntent()
}