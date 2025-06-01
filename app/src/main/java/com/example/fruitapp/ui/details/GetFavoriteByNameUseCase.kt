package com.example.fruitapp.ui.details

import com.example.fruitapp.data.FavoriteDao
import javax.inject.Inject

class GetFavoriteByNameUseCase @Inject constructor(private val favoriteDao: FavoriteDao) {
    operator fun invoke(name: String) = favoriteDao.getFavByName(name)
}