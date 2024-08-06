package com.example.fruitapp.ui.quiz

sealed class QuizIntent {
    data class OnQuestionClick(var questionIndex: Int) : QuizIntent()
    object OnBackClick : QuizIntent()
    data class OnAnswerClick(var questionIndex: Int, var answerIndex: Int) : QuizIntent()
    object OnCheckClick : QuizIntent()
}