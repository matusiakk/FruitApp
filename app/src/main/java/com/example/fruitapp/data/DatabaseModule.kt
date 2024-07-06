package com.example.fruitapp.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideFavoriteDb(@ApplicationContext context: Context): FavoriteDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            FavoriteDatabase::class.java,
            "favorite_database"
        ).build()
    }

    @Provides
    fun provideFavoriteDao(favoriteDb: FavoriteDatabase): FavoriteDao {
        return favoriteDb.favoriteDao()
    }
}
