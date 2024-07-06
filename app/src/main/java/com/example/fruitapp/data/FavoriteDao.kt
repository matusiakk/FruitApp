package com.example.fruitapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert
    fun insertFav(fruit: Favorite)

    @Query("DELETE FROM favorite WHERE fruit_name = :name")
    fun deleteFav(name: String)

    @Query("SELECT fruit_name FROM favorite")
    fun getAllFav(): List<String>

    @Query("SELECT * FROM favorite WHERE fruit_name = :name")
    fun getFavByName(name: String): Favorite


}