package com.example.fruitapp.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MyApi {
    val BASE_URL = "https://fruityvice.com/api/fruit/"

    @Provides
    @Singleton
    fun provideFruitApi(): FruitApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(FruitApi::class.java)
    }

}

interface FruitApi {
    @GET("{name}")
    suspend fun getFruitDetails(@Path("name") name: String): Fruit

}

