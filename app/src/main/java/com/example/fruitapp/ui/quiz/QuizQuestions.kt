package com.example.fruitapp.ui.quiz

import com.example.fruitapp.R

enum class QuizQuestions(
    val question: Int, val answerA: Int, val answerB: Int, val answerC: Int, val answerD: Int
) {
    QUESTION1(
        R.string.question1,
        R.string.answer1a,
        R.string.answer1b,
        R.string.answer1c,
        R.string.answer1d
    ),
    QUESTION2(
        R.string.question2,
        R.string.answer2a,
        R.string.answer2b,
        R.string.answer2c,
        R.string.answer2d
    ),
    QUESTION3(
        R.string.question3,
        R.string.answer3a,
        R.string.answer3b,
        R.string.answer3c,
        R.string.answer3d
    ),
    QUESTION4(
        R.string.question4,
        R.string.answer4a,
        R.string.answer4b,
        R.string.answer4c,
        R.string.answer4d
    ),
    QUESTION5(
        R.string.question5,
        R.string.answer5a,
        R.string.answer5b,
        R.string.answer5c,
        R.string.answer5d
    );

    companion object {
        fun getQuizResult(answers: List<Int?>): Int {
            val answerCounts = answers.groupingBy { it }.eachCount()
            val resultId = when (answerCounts.maxByOrNull { it.value }?.key) {
                1 -> (R.string.result1)
                2 -> (R.string.result2)
                3 -> (R.string.result3)
                else -> (R.string.result4)
            }
            return resultId
        }
    }
}
