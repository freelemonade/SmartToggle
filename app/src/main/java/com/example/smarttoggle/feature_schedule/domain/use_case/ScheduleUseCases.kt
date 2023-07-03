package com.example.smarttoggle.feature_schedule.domain.use_case

data class ScheduleUseCases(
    val getSchedules: GetSchedules,
    val deleteSchedule: DeleteSchedule,
    val addSchedule: AddSchedule,
    val getSchedule: GetSchedule
)
