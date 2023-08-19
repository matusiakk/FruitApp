package com.example.fruitapp.ui.start

sealed class StartIntent {
    data class EnterText(val text: String): StartIntent()
    object OnSearchButtonClick: StartIntent()
    object OnListButtonClick: StartIntent()
}