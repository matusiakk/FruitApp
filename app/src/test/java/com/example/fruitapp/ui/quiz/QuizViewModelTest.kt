package com.example.fruitapp.ui.quiz


import com.example.fruitapp.nav.NavEvent
import com.example.fruitapp.nav.Navigator
import io.mockk.mockkObject
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import com.example.fruitapp.R

class QuizViewModelTest {

    private lateinit var sut: QuizViewModel

    @Test
    fun `should navigate back when back click`() = runTest {
        //Arrange
        setup()
        //Act
        sut.onIntent(QuizIntent.OnBackClick)
        //Assert
        verify(exactly = 1) { Navigator.sendEvent(NavEvent.NavigateBack) }
    }

    @Test
    fun `should expanded question after click`() = runTest {
        //Arrange
        setup()
        //Act
        sut.onIntent(QuizIntent.OnQuestionClick(1))
        //Assert
        assertTrue(sut.state.value.questionsExpanded[1])
    }

    @Test
    fun `should update state with selected answer`() = runTest {
        //Arrange
        setup()
        //Act
        sut.onIntent(QuizIntent.OnAnswerClick(2, 4))
        //Assert
        assertEquals(4, sut.state.value.selectedAnswers[2])
    }

    @Test
    fun `should update state with result after check click`() = runTest {
        //Arrange
        val initialState = QuizState(selectedAnswers = listOf(2, 2, 2, 1, 4))
        setup(initialState)
        //Act
        sut.onIntent(QuizIntent.OnCheckClick)
        //Assert

        assertEquals(R.string.result2, sut.state.value.result)
    }

    @Test
    fun `should show result after check click`() = runTest {
        //Arrange
        setup()
        //Act
        sut.onIntent(QuizIntent.OnCheckClick)
        //Assert
        assertTrue(sut.state.value.showResult)
    }

    private fun setup(initialState: QuizState = QuizState()) {
        mockkObject(Navigator)
        sut = QuizViewModel(initialState)
    }

}