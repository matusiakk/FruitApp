package com.example.fruitapp.ui.details

sealed class DetailsIntent {
    object OnBackButtonClick : DetailsIntent()
    object OnPexelsClick : DetailsIntent()
    data class OnFavoriteClick(var name: String) : DetailsIntent()
}
