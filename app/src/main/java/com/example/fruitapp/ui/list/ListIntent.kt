package com.example.fruitapp.ui.list

sealed class ListIntent {
    data class OnFruitClick(var name: String) : ListIntent()
    object OnBackClick : ListIntent()
    object OnAscendingClick : ListIntent()
    object OnDescendingClick : ListIntent()
    object OnSortingClick : ListIntent()
    object OnSortingDismiss : ListIntent()
    object OnOptionClick : ListIntent()
    object OnOptionDismiss : ListIntent()
    data class SelectOption(var option: String) : ListIntent()
}