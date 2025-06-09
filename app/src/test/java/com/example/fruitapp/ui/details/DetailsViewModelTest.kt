package com.example.fruitapp.ui.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.example.fruitapp.data.Favorite
import com.example.fruitapp.data.Fruit
import com.example.fruitapp.data.Image
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
    private val orange = mockk<Fruit>(relaxed = true)
    {every {name} returns "Orange"}
    private val apple = mockk<Fruit>(relaxed = true)
    {every {name} returns "Apple"}

    private val image = mockk<Image>(relaxed = true)


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