package com.example.fruitapp.ui.start

import com.example.fruitapp.data.FruitApi
import javax.inject.Inject

class SearchFruitByNameUseCase @Inject constructor (private val api: FruitApi) {
    suspend operator fun invoke(name: String) = api.getFruitDetails(name)
}