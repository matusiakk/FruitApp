package com.example.fruitapp.ui.about

import androidx.lifecycle.ViewModel
import com.example.fruitapp.nav.NavEvent
import com.example.fruitapp.nav.Navigator

class AboutViewModel : ViewModel() {


    fun onIntent(intent: AboutIntent) {
        when (intent) {
            is AboutIntent.OnBackClick -> onBackClick()
        }
    }

    private fun onBackClick() {
        Navigator.sendEvent(NavEvent.NavigateBack)
    }
}