package com.example.smarttoggle.feature_schedule.presentation.util

sealed class Screen(val route: String) {
    object SchedulesScreen : Screen("schedules_screen")
    object AddEditScheduleScreen : Screen("add_edit_schedule_screen")
}
