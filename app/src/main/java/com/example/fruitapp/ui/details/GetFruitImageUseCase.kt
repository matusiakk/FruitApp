package com.example.fruitapp.ui.details

import com.example.fruitapp.data.ImageApi
import javax.inject.Inject

class GetFruitImageUseCase @Inject constructor(private val imageApi: ImageApi) {
    suspend operator fun invoke(name: String) = imageApi.getImage(name)
}
