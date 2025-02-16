package com.example.fruitapp.ui.about

import com.example.fruitapp.nav.NavEvent
import com.example.fruitapp.nav.Navigator
import io.mockk.mockkObject
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class AboutViewModelTest {

    private lateinit var sut: AboutViewModel

@Test
fun `should navigate back when back click`() = runTest {
    //Arrange
    setup()
    //Act
    sut.onIntent(AboutIntent.OnBackClick)
    //Assert
    verify(exactly = 1){Navigator.sendEvent(NavEvent.NavigateBack)}
}

private fun setup(){
    mockkObject(Navigator)
    sut = AboutViewModel()
}

}