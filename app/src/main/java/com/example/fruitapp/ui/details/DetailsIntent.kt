package com.example.fruitapp.ui.details

sealed class DetailsIntent {
    data class LoadDetails(val name: String): DetailsIntent()
    object OnBackButtonClick: DetailsIntent()
}