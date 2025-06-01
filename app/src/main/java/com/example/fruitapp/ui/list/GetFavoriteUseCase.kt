package com.example.fruitapp.ui.list

import com.example.fruitapp.data.FavoriteDao
import com.example.fruitapp.data.Fruit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(private val favoriteDao: FavoriteDao) {
    suspend operator fun invoke(fruitList: List<Fruit>): List<Fruit> = withContext(Dispatchers.IO) {
        val favoriteNamesList = favoriteDao.getAllFav()
        fruitList.filter {
            it.name in favoriteNamesList
        }
    }
}