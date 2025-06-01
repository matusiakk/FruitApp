package com.example.fruitapp.ui.details

import com.example.fruitapp.data.Favorite
import com.example.fruitapp.data.FavoriteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddToFavoriteUseCase @Inject constructor(private val favoriteDao: FavoriteDao) {
    suspend operator fun invoke(name: String) = withContext(Dispatchers.IO) {
        favoriteDao.insertFav(Favorite(fruitName = name))
    }
}