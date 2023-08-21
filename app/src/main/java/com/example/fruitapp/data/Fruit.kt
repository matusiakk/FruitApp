package com.example.fruitapp.data

data class Fruit(
    val family: String,
    val genus: String,
    val id: Int,
    val name: String,
    val nutritions: Nutritions,
    val order: String
)

data class Nutritions(
    val calories: Int,
    val carbohydrates: Double,
    val fat: Double,
    val protein: Double,
    val sugar: Double
)

