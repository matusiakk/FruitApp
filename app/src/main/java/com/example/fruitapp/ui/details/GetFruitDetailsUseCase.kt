package com.example.fruitapp.ui.details

import com.example.fruitapp.data.FruitApi
import javax.inject.Inject

class GetFruitDetailsUseCase @Inject constructor(private val api: FruitApi) {
    suspend operator fun invoke(name: String) = api.getFruitDetails(name)
}