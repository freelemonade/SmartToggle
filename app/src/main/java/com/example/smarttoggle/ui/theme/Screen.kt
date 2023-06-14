package com.example.smarttoggle.ui.theme

sealed class Screen(val route: String) {
    object ScheduleScreen : Screen("schedulescreen")
    object LocationScreen : Screen("locationscreen")
    object ScheduleInputScreen : Screen("scheduleinputscreen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
