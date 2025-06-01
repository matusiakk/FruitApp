package com.example.fruitapp.ui.start

import com.example.fruitapp.data.Fruit
import com.example.fruitapp.data.FruitApi
import javax.inject.Inject

class GetFruitListUseCase @Inject constructor (private val api: FruitApi){
    suspend operator fun invoke(): List<Fruit>{
        return api.getList()
    }
}