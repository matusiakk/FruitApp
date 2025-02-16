package com.example.fruitapp.data

import com.example.fruitapp.ui.start.StartState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MyApi {
    val FRUIT_BASE_URL = "https://fruityvice.com/api/fruit/"
    val IMAGE_BASE_URL = "https://api.pexels.com/v1/"

    @Provides
    @Singleton
    fun provideFruitApi(): FruitApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(FRUIT_BASE_URL)
            .build()
            .create(FruitApi::class.java)
    }

    @Provides
    @Singleton
    fun provideImageApi(): ImageApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(IMAGE_BASE_URL)
            .build()
            .create(ImageApi::class.java)
    }
    @Provides
    fun provideStartState(): StartState {
        return StartState()
    }
}

interface FruitApi {
    @GET("{name}")
    suspend fun getFruitDetails(@Path("name") name: String): Fruit

    @GET("all")
    suspend fun getList(): List<Fruit>
}

interface ImageApi {
    @Headers("Authorization: GGCxH7nFv4o1gbR0DRhTf5rGazUn6qDZUy9rxhFBsDzpGz5KKdCtlDMg")
    @GET("search")
    suspend fun getImage(
        @Query("query") name: String,
        @Query("per_page") perPage: Int = 1
    ): Image
}

