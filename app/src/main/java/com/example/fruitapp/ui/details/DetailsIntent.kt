package com.example.fruitapp.ui.details

sealed class DetailsIntent {
    object OnBackButtonClick : DetailsIntent()
    data class OnFavoriteClick(var name: String) : DetailsIntent()
}
