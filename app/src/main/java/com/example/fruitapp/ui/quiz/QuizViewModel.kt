package com.example.fruitapp.ui.quiz

import androidx.lifecycle.ViewModel
import com.example.fruitapp.nav.NavEvent
import com.example.fruitapp.nav.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class QuizViewModel : ViewModel() {
    private val _state = MutableStateFlow(QuizState())
    val state = _state.asStateFlow()


    fun onIntent(intent: QuizIntent) {
        when (intent) {
            is QuizIntent.OnQuestionClick -> onQuestionClick(intent.questionIndex)
            is QuizIntent.OnBackClick -> onBackClick()
            is QuizIntent.OnAnswerClick -> onAnswerClick(intent.questionIndex, intent.answerIndex)
            is QuizIntent.OnCheckClick -> onCheckClick()
        }
    }

    private fun onBackClick() {
        Navigator.sendEvent(NavEvent.NavigateBack)
    }

    private fun onQuestionClick(questionIndex: Int) {
        _state.update {
            it.copy(questions = it.questions.mapIndexed { i, question ->
                if (i == questionIndex) question.copy(expanded = !question.expanded) else question
            })
        }

    }

    private fun onAnswerClick(questionIndex: Int, answerIndex: Int) {
        val updatedSelectedAnswers = state.value.selectedAnswers.toMutableList()
        updatedSelectedAnswers[questionIndex] = answerIndex
        _state.update {
            it.copy(
                selectedAnswers = updatedSelectedAnswers,
                questions = it.questions.mapIndexed { i, question ->
                    when (i) {
                        questionIndex -> question.copy(expanded = false)
                        questionIndex + 1 -> question.copy(expanded = true)
                        else -> question
                    }
                }
            )
        }
    }

    private fun onCheckClick() {
        val answers = state.value.selectedAnswers
        val answersA = answers.count { it == 1 }
        val answersB = answers.count { it == 2 }
        val answersC = answers.count { it == 3 }
        val answersD = answers.count { it == 4 }

        _state.update {
            it.copy(
                result = maxOf(answersA, answersB, answersC, answersD),
                showResult = true
            )
        }
    }
}