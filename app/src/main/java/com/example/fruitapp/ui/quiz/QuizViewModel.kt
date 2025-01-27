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
            it.copy(questionsExpanded = List(it.questionsExpanded.size) { i ->
                i == questionIndex
            })
        }

    }

    private fun onAnswerClick(questionIndex: Int, answerIndex: Int) {
        val updatedSelectedAnswers = state.value.selectedAnswers.toMutableList()
        updatedSelectedAnswers[questionIndex] = answerIndex
        _state.update {
            it.copy(
                selectedAnswers = updatedSelectedAnswers,
                questionsExpanded = List(it.questionsExpanded.size) { i ->
                    when (i) {
                        questionIndex -> false
                        questionIndex + 1 -> true
                        else -> false
                    }
                }
            )
        }
    }

    private fun onCheckClick() {
        val answers = state.value.selectedAnswers
        val result = QuizQuestions.getQuizResult(answers)

        _state.update {
            it.copy(
                result = result,
                showResult = true
            )
        }
    }
}