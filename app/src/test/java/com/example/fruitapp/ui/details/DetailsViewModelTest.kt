package com.example.fruitapp.ui.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.example.fruitapp.data.Favorite
import com.example.fruitapp.data.Fruit
import com.example.fruitapp.data.Image
import com.example.fruitapp.data.Nutritions
import com.example.fruitapp.data.Photo
import com.example.fruitapp.data.Src
import com.example.fruitapp.nav.NavEvent
import com.example.fruitapp.nav.Navigator
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DetailsViewModelTest {

    private lateinit var sut: DetailsViewModel
    private lateinit var getFruitDetailsUseCase: GetFruitDetailsUseCase
    private lateinit var getFruitImageUseCase: GetFruitImageUseCase
    private lateinit var getFavoriteByNameUseCase: GetFavoriteByNameUseCase
    private lateinit var addToFavoriteUseCase: AddToFavoriteUseCase
    private lateinit var removeFromFavoriteUseCase: RemoveFromFavoriteUseCase
    private val testDispatcher = StandardTestDispatcher()
    private val savedStateHandleOrange: SavedStateHandle = SavedStateHandle().apply {
        set("name", "orange")
    }
    private val savedStateHandleApple: SavedStateHandle = SavedStateHandle().apply {
        set("name", "apple")
    }
    private val orange =
        Fruit("Rutaceae", "Citrus", 3, "Orange", Nutritions(47, 12.0, 0.1, 0.9, 9.0), "Sapindales")
    private val apple =
        Fruit("Rosaceae", "Malus", 1, "Apple", Nutritions(52, 14.0, 0.2, 0.3, 10.0), "Rosales")

    private val image = Image(
        next_page = "https://api.example.com/images?page=2",
        page = 1,
        per_page = 10,
        total_results = 100,
        photos = listOf(
            Photo(
                alt = "Test",
                avg_color = "#FFA500",
                height = 4000,
                id = 12345,
                liked = false,
                photographer = "John Doe",
                photographer_id = 67890,
                photographer_url = "https://example.com/photographer/john-doe",
                url = "https://example.com/photo/12345",
                width = 6000,
                src = Src(
                    landscape = "https://example.com/images/landscape.jpg",
                    large = "https://example.com/images/large.jpg",
                    large2x = "https://example.com/images/large2x.jpg",
                    medium = "https://example.com/images/medium.jpg",
                    original = "https://example.com/images/original.jpg",
                    portrait = "https://example.com/images/portrait.jpg",
                    small = "https://example.com/images/small.jpg",
                    tiny = "https://example.com/images/tiny.jpg"
                )
            )
        )
    )


    @Test
    fun `should fetch fruit details from API and update state on init`() = runTest {
        //Arrange
        setup()
        //Act
        //Assert
        assertEquals(orange, sut.state.value.fruit)
    }

    @Test
    fun `should set isFav to true when fruit is in favorites`() = runTest {
        //Arrange
        setup()
        //Act
        //Assert
        assertTrue(sut.state.value.isFav)
    }

    @Test
    fun `should set isFav to false when fruit is not in favorites`() = runTest {
        //Arrange
        setup(savedStateHandleApple)
        //Act
        //Assert
        assertFalse(sut.state.value.isFav)
    }

    @Test
    fun `should fetch fruit image from API and update state on init`() = runTest {
        //Arrange
        setup()
        //Act
        //Assert
        assertEquals(image, sut.state.value.fruitImage)
    }

    @Test
    fun `should navigate back when back click`() = runTest {
        //Arrange
        setup()
        //Act
        sut.onIntent(DetailsIntent.OnBackButtonClick)
        //Assert
        verify(exactly = 1) { Navigator.sendEvent(NavEvent.NavigateBack) }
    }

    @Test
    fun `should delete fruit from favorites after favorite button click when fruit is in favorites`() =
        runTest {
            //Arrange
            setup()
            //Act
            sut.onIntent(DetailsIntent.OnFavoriteClick("orange"))
            testDispatcher.scheduler.advanceUntilIdle()
            //Assert
            coVerify { removeFromFavoriteUseCase("orange") }
        }


    @Test
    fun `should add fruit to favorites after favorite button click when fruit is not in favorites`() =
        runTest {
            //Arrange
            setup(savedStateHandleApple)
            //Act
            sut.onIntent(DetailsIntent.OnFavoriteClick("apple"))
            testDispatcher.scheduler.advanceUntilIdle()
            //Assert
            coVerify { addToFavoriteUseCase(("apple")) }
        }


    private fun setup(savedStateHandle: SavedStateHandle = savedStateHandleOrange) {
        Dispatchers.setMain(testDispatcher)
        getFruitDetailsUseCase = mockk()
        getFruitImageUseCase = mockk()
        getFavoriteByNameUseCase = mockk()
        addToFavoriteUseCase = mockk()
        removeFromFavoriteUseCase = mockk()
        mockkStatic(android.util.Log::class)
        mockkObject(Navigator)
        every { Log.e(any(), any(), any()) } returns 0
        coEvery { getFruitDetailsUseCase("orange") } returns orange
        coEvery { getFruitDetailsUseCase("apple") } returns apple
        coEvery { getFruitImageUseCase("orange") } returns image
        coEvery { getFruitImageUseCase("apple") } returns image
        every { getFavoriteByNameUseCase("orange") } returns Favorite(1, "Orange")
        every { getFavoriteByNameUseCase("apple") } returns null
        coEvery { removeFromFavoriteUseCase("orange") } just Runs
        coEvery { addToFavoriteUseCase("apple") } just Runs

        sut = DetailsViewModel(
            savedStateHandle,
            getFruitDetailsUseCase,
            getFruitImageUseCase,
            getFavoriteByNameUseCase,
            addToFavoriteUseCase,
            removeFromFavoriteUseCase
        )
        testDispatcher.scheduler.advanceUntilIdle()
    }
}