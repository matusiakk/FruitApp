package com.example.fruitapp.data

data class Question(
    val question: String,
    val answers: List<String>,
    val expanded: Boolean
)
