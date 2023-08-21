package com.example.fruitapp.nav

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow

object Navigator {
    private val navEvents = Channel<NavEvent>()
    fun sendEvent(event: NavEvent) {
        navEvents.trySend(event)
    }

    fun getEventsFlow() = navEvents.consumeAsFlow()
}

sealed interface NavEvent {
    data class NavigateTo(val route: String) : NavEvent
    object NavigateBack : NavEvent
}