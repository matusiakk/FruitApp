package com.example.fruitapp.ui.start

sealed class StartIntent {
    data class EnterText(val text: String) : StartIntent()
    object OnSearchClick : StartIntent()
    object OnListButtonClick : StartIntent()
    object OnCloseClick : StartIntent()
    data class OnSearchItemClick(val name: String) : StartIntent()
}