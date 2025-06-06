package com.example.fruitapp.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fruitapp.nav.NavEvent
import com.example.fruitapp.nav.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.fruitapp.data.Fruit
import com.example.fruitapp.nav.Screen
import kotlinx.coroutines.flow.update

@HiltViewModel
class StartViewModel @Inject constructor(
    private val getFruitListUseCase: GetFruitListUseCase,
    private val searchFruitByNameUseCase: SearchFruitByNameUseCase,
    initialState: StartState
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<StartState> = _state

    private var fruitList = emptyList<Fruit>()

    init {
        getList()
    }

    private fun getList() {
        viewModelScope.launch {
            fruitList = getFruitListUseCase()
        }
    }

    fun onIntent(intent: StartIntent) {
        when (intent) {
            is StartIntent.EnterText -> enterText(intent.text)
            is StartIntent.OnSearchClick -> onSearchButtonClick()
            is StartIntent.OnListButtonClick -> onListButtonClick()
            is StartIntent.OnCloseClick -> onCloseClick()
            is StartIntent.OnSearchItemClick -> onSearchItemClick(intent.name)
            is StartIntent.OnFavoriteClick -> onFavoriteClick()
            is StartIntent.OnAboutAppClick -> onAboutAppClick()
            is StartIntent.OnQuizButtonClick -> onQuizButtonClick()
        }
    }

    private fun onQuizButtonClick() {
        Navigator.sendEvent(NavEvent.NavigateTo(Screen.QuizScreen.route))
    }

    private fun onAboutAppClick() {
        Navigator.sendEvent(NavEvent.NavigateTo(Screen.AboutScreen.route))
    }

    private fun onFavoriteClick() {
        Navigator.sendEvent(NavEvent.NavigateTo(Screen.ListScreen.withOptionalArgs(true)))
    }

    private fun onSearchItemClick(name: String) {
        Navigator.sendEvent(NavEvent.NavigateTo(Screen.DetailsScreen.withArgs(name)))
        _state.update {
            it.copy(
                text = "",
                filteredFruitList = emptyList()
            )
        }
    }

    private fun onCloseClick() {
        _state.update {
            it.copy(
                text = "",
                filteredFruitList = emptyList()
            )
        }
    }

    private fun enterText(text: String) {
        if (text.all { it.isLetter() })
            _state.update {
                it.copy(text = text)
            }
        if (text.length > 1)
            _state.update {
                it.copy(
                    filteredFruitList = fruitList.filter {
                         it.name.contains(text, ignoreCase = true)
                    })
            }
        else _state.update { it.copy(filteredFruitList = emptyList()) }
    }

    private fun onSearchButtonClick() {

        val text = _state.value.text

        if (text.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    _state.update {
                        it.copy(
                            fruit = searchFruitByNameUseCase(text),
                            text = "",
                            filteredFruitList = emptyList()
                        )
                    }
                    Navigator.sendEvent(NavEvent.NavigateTo(Screen.DetailsScreen.withArgs(text)))

                } catch (e: Exception) {
                    _state.update {
                        it.copy(
                            fruit = null
                        )
                    }
                }
            }
        }
    }

    private fun onListButtonClick() {
        Navigator.sendEvent(NavEvent.NavigateTo(Screen.ListScreen.route))
    }
}

