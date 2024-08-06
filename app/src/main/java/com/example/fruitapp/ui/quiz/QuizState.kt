package com.example.fruitapp.ui.quiz

import com.example.fruitapp.data.Question

data class QuizState(
    val questions: List<Question> = listOf(
        Question(
            "1. How would you describe your personality?",
            listOf(
                "a) Energetic and cheerful",
                "b) Calm and balanced",
                "c) Sensual and mysterious",
                "d) Caring and warm"
            ),
            true
        ),
        Question(
            "2. How do you spend your free time?",
            listOf(
                "a) Playing sports or exercising",
                "b) Reading books or meditating",
                "c) Exploring new places and discovering the unknown",
                "d) Spending time with family and friends"
            ),
            false
        ),
        Question(
            "3. What is your favorite type of food?",
            listOf(
                "a) Spicy and bold dishes",
                "b) Light and healthy meals",
                "c) Exotic and unusual flavors",
                "d) Traditional, home-cooked food"
            ),
            false
        ),
        Question(
            "4. What do you value most in other people?",
            listOf(
                "a) Sense of humor and optimism",
                "b) Calmness and composure",
                "c) Creativity and originality",
                "d) Empathy and care"
            ),
            false
        ),
        Question(
            "5. How do you react to stress?",
            listOf(
                "a) I find active ways to release tension",
                "b) I try to stay calm and meditate",
                "c) I seek new, exciting challenges",
                "d) I seek support from loved ones"
            ),
            false
        )
    ),
    val selectedAnswers: List<Int?> = List(questions.size) { null },
    val quizResults: List<String> = listOf(
        "You are an Orange!\n" +
                "You are full of energy and optimism. Your presence gives strength and inspiration to others.",
        "You are a Pear!\n" +
                "You are calm and balanced, making people eager to seek your support and advice.",
        "You are a Pineapple!\n" +
                "You are mysterious and exotic, enjoying challenges and new experiences. Your creativity amazes others.",
        "You are an Apple!\n" +
                "You are warm and caring, always ready to help and support. Your home is a place full of love and warmth."
    ),
    val result: Int = 0,
    val showResult: Boolean = false
)
