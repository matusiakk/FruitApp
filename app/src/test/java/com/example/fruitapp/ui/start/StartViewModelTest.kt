package com.example.fruitapp.ui.start

import com.example.fruitapp.data.Fruit
import com.example.fruitapp.data.FruitApi
import com.example.fruitapp.data.Nutritions
import com.example.fruitapp.nav.NavEvent
import com.example.fruitapp.nav.Navigator
import com.example.fruitapp.nav.Screen
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Test

class StartViewModelTest {

    private lateinit var sut: StartViewModel
    private lateinit var api: FruitApi
    private val testDispatcher = StandardTestDispatcher()
    private val fruitList = listOf(
        Fruit("Rosaceae", "Malus", 1, "Apple", Nutritions(52, 14.0, 0.2, 0.3, 10.0), "Rosales"),
        Fruit("Musaceae", "Musa", 2, "Banana", Nutritions(89, 23.0, 0.3, 1.1, 12.0), "Zingiberales"),
        Fruit("Rutaceae", "Citrus", 3, "Orange", Nutritions(47, 12.0, 0.1, 0.9, 9.0), "Sapindales")
    )
    private val fruit =
        Fruit("Rutaceae", "Citrus", 3, "Orange", Nutritions(47, 12.0, 0.1, 0.9, 9.0), "Sapindales")


    @Test
    fun `should not update text state when entered character other than letter`() = runTest {
        //Arrange
        setup()
        //Act
        sut.onIntent(StartIntent.EnterText("!"))
        //Assert
        assertEquals("", sut.state.value.text)
    }

    @Test
    fun `should update text state when entered letter`() = runTest {
        //Arrange
        setup()
        //Act
        sut.onIntent(StartIntent.EnterText("a"))
        //Assert
        assertEquals("a", sut.state.value.text)
    }

    @Test
    fun `should not filter list state when entered text has less than two letters`() = runTest {
        //Arrange
        setup()
        //Act
        sut.onIntent(StartIntent.EnterText("a"))
        //Assert
        assertEquals(emptyList<Fruit>(), sut.state.value.filteredFruitList)
    }

    @Test
    fun `should update state with filtered list when enter more than one letter`() = runTest {
        //Arrange
        val expectedFruitList = listOf(
            Fruit(
                "Musaceae",
                "Musa",
                2,
                "Banana",
                Nutritions(89, 23.0, 0.3, 1.1, 12.0),
                "Zingiberales"
            )
        )
        setup()
        //Act
        sut.onIntent(StartIntent.EnterText("ba"))
        //Assert
        assertEquals(expectedFruitList, sut.state.value.filteredFruitList)
    }

    @Test
    fun `should update state with fruit details when search button click`() = runTest {
        //Arrange
        setup(initialState = StartState(text = "orange"))
        coEvery { api.getFruitDetails("orange") } returns fruit
        //Act
        sut.onIntent(StartIntent.OnSearchClick)
        testDispatcher.scheduler.advanceUntilIdle()
        //Assert
        assertEquals(
            fruit,
            sut.state.value.fruit
        )
    }

    @Test
    fun `should navigate to details screen after search button click`() = runTest {
        //Arrange
        setup(initialState = StartState(text = "orange"))
        coEvery { api.getFruitDetails("orange") } returns fruit
        //Act
        sut.onIntent(StartIntent.OnSearchClick)
        testDispatcher.scheduler.advanceUntilIdle()
        //Assert
        verify(exactly = 1) {
            Navigator.sendEvent(
                NavEvent.NavigateTo(
                    Screen.DetailsScreen.withArgs(
                        "orange"
                    )
                )
            )
        }
    }

    @Test
    fun `should navigate to details screen after search item click`() = runTest {
        //Arrange
        setup()
        //Act
        sut.onIntent(StartIntent.OnSearchItemClick("apple"))
        //Assert
        verify(exactly = 1) {
            Navigator.sendEvent(
                NavEvent.NavigateTo(
                    Screen.DetailsScreen.withArgs(
                        "apple"
                    )
                )
            )
        }
    }

    @Test
    fun `should navigate to list screen when list button click`() = runTest {
        //Arrange
        setup()
        //Act
        sut.onIntent(StartIntent.OnListButtonClick)
        //Assert
        verify(exactly = 1) { Navigator.sendEvent(NavEvent.NavigateTo(Screen.ListScreen.route)) }
    }

    @Test
    fun `should update state with empty text and empty fruit list after close click`() = runTest {
        //Arrange
        setup(initialState = StartState(text = "test"))
        //Act
        sut.onIntent(StartIntent.OnCloseClick)
        //Assert
        assertEquals("", sut.state.value.text)
    }

    @Test
    fun `should navigate to list screen with argument favorite true when favorite button click`() =
        runTest {
            //Arrange
            setup()
            //Act
            sut.onIntent(StartIntent.OnFavoriteClick)
            //Assert
            verify(exactly = 1) {
                Navigator.sendEvent(
                    NavEvent.NavigateTo(
                        Screen.ListScreen.withOptionalArgs(
                            true
                        )
                    )
                )
            }
        }

    @Test
    fun `should navigate to about screen when about button click`() = runTest {
        //Arrange
        setup()
        //Act
        sut.onIntent(StartIntent.OnAboutAppClick)
        //Assert
        verify(exactly = 1) { Navigator.sendEvent(NavEvent.NavigateTo(Screen.AboutScreen.route)) }
    }

    @Test
    fun `should navigate to quiz screen when quiz button click`() = runTest {
        //Arrange
        setup()
        //Act
        sut.onIntent(StartIntent.OnQuizButtonClick)
        //Assert
        verify(exactly = 1) { Navigator.sendEvent(NavEvent.NavigateTo(Screen.QuizScreen.route)) }
    }


    private fun setup(initialState: StartState = StartState()) {
        Dispatchers.setMain(testDispatcher)
        api = mockk()
        coEvery { api.getList() } returns fruitList
        mockkObject(Navigator)
        sut = StartViewModel(
            api = api,
            initialState = initialState
        )
        testDispatcher.scheduler.advanceUntilIdle()
    }
}