package com.example.fruitapp.ui.list

import androidx.lifecycle.SavedStateHandle
import com.example.fruitapp.data.FavoriteDao
import com.example.fruitapp.data.Fruit
import com.example.fruitapp.data.FruitApi
import com.example.fruitapp.data.Nutritions
import com.example.fruitapp.nav.NavEvent
import com.example.fruitapp.nav.Navigator
import com.example.fruitapp.nav.Screen
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Test

class ListViewModelTest {

private lateinit var sut: ListViewModel
private lateinit var api: FruitApi
private lateinit var favoriteDao: FavoriteDao
private val testDispatcher = StandardTestDispatcher()
private val savedStateHandleFalse: SavedStateHandle = SavedStateHandle().apply {
    set("favorite", false)
}
    private val savedStateHandleTrue: SavedStateHandle = SavedStateHandle().apply {
        set("favorite", true)
    }
    private val fruitList = listOf(
        Fruit("Rosaceae", "Malus", 1, "Apple", Nutritions(52, 14.0, 0.2, 0.3, 10.0), "Rosales"),
        Fruit("Musaceae", "Musa", 2, "Banana", Nutritions(89, 23.0, 0.3, 1.1, 12.0), "Zingiberales"),
        Fruit("Rutaceae", "Citrus", 3, "Orange", Nutritions(47, 12.0, 0.1, 0.9, 9.0), "Sapindales")
    )

    @Test
    fun `on init should update state with full fruit list when favorite is false`() = runTest {
        //Arrange
        setup()
        //Act
        //Assert
        assertEquals(fruitList, sut.state.value.fruitList)
    }

    @Test
    fun `on init should update list state with favorite fruits when favorite is true`() = runTest {
        //Arrange
        setup(savedStateHandleTrue)
        //Act
        //Assert
        assertEquals(listOf(
            Fruit("Rosaceae", "Malus", 1, "Apple", Nutritions(52, 14.0, 0.2, 0.3, 10.0), "Rosales"),
            Fruit("Rutaceae", "Citrus", 3, "Orange", Nutritions(47, 12.0, 0.1, 0.9, 9.0), "Sapindales")
        ), sut.state.value.fruitList)
    }

    @Test
    fun `should update list state with favorite fruits when favorite click`() = runTest {
        //Arrange
        setup()
        //Act
        sut.onIntent(ListIntent.OnFavoriteClick)
        //Assert
        assertEquals(listOf(
            Fruit("Rosaceae", "Malus", 1, "Apple", Nutritions(52, 14.0, 0.2, 0.3, 10.0), "Rosales"),
            Fruit("Rutaceae", "Citrus", 3, "Orange", Nutritions(47, 12.0, 0.1, 0.9, 9.0), "Sapindales")
        ), sut.state.value.fruitList)
    }

    @Test
    fun `should navigate to fruit details screen when fruit click`() = runTest {
        //Arrange
        setup()
        //Act
        sut.onIntent(ListIntent.OnFruitClick("apple"))
        //Assert
        verify { Navigator.sendEvent(NavEvent.NavigateTo(Screen.DetailsScreen.withArgs("apple"))) }
    }

    @Test
    fun `should navigate back when back click`() = runTest {
        //Arrange
        setup()
        //Act
        sut.onIntent(ListIntent.OnBackClick)
        //Assert
        verify { Navigator.sendEvent(NavEvent.NavigateBack) }
    }

    @Test
    fun `should show sorting menu when sorting click`() = runTest {
        //Arrange
        setup()
        //Act
        sut.onIntent(ListIntent.OnSortingClick)
        //Assert
        assertTrue(sut.state.value.isSortingMenuVisible)
    }

    @Test
    fun `should updated state with sorted by descending fruit list when descending click`() = runTest {
        //Arrange
        setup()
        //Act
        sut.onIntent(ListIntent.OnDescendingClick)
        //Assert
        assertEquals(listOf(
            Fruit("Musaceae", "Musa", 2, "Banana", Nutritions(89, 23.0, 0.3, 1.1, 12.0), "Zingiberales"),
            Fruit("Rosaceae", "Malus", 1, "Apple", Nutritions(52, 14.0, 0.2, 0.3, 10.0), "Rosales"),
            Fruit("Rutaceae", "Citrus", 3, "Orange", Nutritions(47, 12.0, 0.1, 0.9, 9.0), "Sapindales")
        ), sut.state.value.fruitList)
    }

    @Test
    fun `should updated state with sorted by ascending fruit list when descending click`() = runTest {
        //Arrange
        setup()
        //Act
        sut.onIntent(ListIntent.OnAscendingClick)
        //Assert
        assertEquals(listOf(
            Fruit("Rutaceae", "Citrus", 3, "Orange", Nutritions(47, 12.0, 0.1, 0.9, 9.0), "Sapindales"),
            Fruit("Rosaceae", "Malus", 1, "Apple", Nutritions(52, 14.0, 0.2, 0.3, 10.0), "Rosales"),
            Fruit("Musaceae", "Musa", 2, "Banana", Nutritions(89, 23.0, 0.3, 1.1, 12.0), "Zingiberales")
        ), sut.state.value.fruitList)
    }

    @Test
    fun `should not show sorting menu when sorting dismiss`() = runTest {
        //Arrange
        setup()
        //Act
        sut.onIntent(ListIntent.OnSortingDismiss)
        //Assert
        assertFalse(sut.state.value.isSortingMenuVisible)
    }

    @Test
    fun `should show option menu when option click`() = runTest {
        //Arrange
        setup()
        //Act
        sut.onIntent(ListIntent.OnOptionClick)
        //Assert
        assertTrue(sut.state.value.isOptionMenuVisible)
    }

    @Test
    fun `should not show option menu when option dismiss`() = runTest {
        //Arrange
        setup()
        //Act
        sut.onIntent(ListIntent.OnOptionDismiss)
        //Assert
        assertFalse(sut.state.value.isOptionMenuVisible)
    }

    @Test
    fun `should update nutrition option state when option selected`() = runTest {
        //Arrange
        setup()
        //Act
        sut.onIntent(ListIntent.SelectOption(NutritionOptions.Sugar))
        //Assert
        assertEquals(NutritionOptions.Sugar, sut.state.value.nutrition)
    }

private fun setup(savedStateHandle: SavedStateHandle = savedStateHandleFalse){
    Dispatchers.setMain(testDispatcher)
    api = mockk()
    favoriteDao = mockk()
    mockkObject(Navigator)
    coEvery { api.getList() } returns fruitList
    every { favoriteDao.getAllFav() } returns (listOf("Apple", "Orange"))
    sut = ListViewModel(
        api = api,
        favoriteDao = favoriteDao,
        savedStateHandle = savedStateHandle
    )
    testDispatcher.scheduler.advanceUntilIdle()
}
}