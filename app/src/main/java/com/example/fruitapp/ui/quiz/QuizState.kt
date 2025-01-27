package com.example.fruitapp.ui.quiz

data class QuizState(
    val questionsExpanded: List<Boolean> = listOf(true, false, false, false, false),
    val selectedAnswers: List<Int?> = List(QuizQuestions.values().size) { null },
    val result: Int = 0,
    val showResult: Boolean = false
)
