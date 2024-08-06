package com.example.fruitapp.nav

sealed class Screen(val route: String) {
    object StartScreen : Screen("start")
    object DetailsScreen : Screen("details")
    object ListScreen : Screen("list")
    object AboutScreen : Screen("about")
    object QuizScreen : Screen("quiz")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    fun withOptionalArgs(favorite: Boolean? = null): String {
        return buildString {
            append(route)
            if (favorite != null) {
                append("?favorite=$favorite")
            }
        }
    }
}
