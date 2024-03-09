package com.example.fruitapp.data

import com.example.fruitapp.ui.list.NutritionOptions

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
) {
    operator fun get(nutrition: NutritionOptions): Any {
        return when (nutrition) {
            NutritionOptions.Calories -> calories
            NutritionOptions.Carbohydrates -> carbohydrates
            NutritionOptions.Fat -> fat
            NutritionOptions.Protein -> protein
            NutritionOptions.Sugar -> sugar
        }
    }
}

