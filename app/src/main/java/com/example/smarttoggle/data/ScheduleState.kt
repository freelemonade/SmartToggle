package com.example.smarttoggle.data

data class ScheduleState(
    val schedules: List<Schedule> = emptyList(),
    val scheduleName: String = "",
    val mode: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val sunday: Boolean = false,
    val monday: Boolean = true,
    val tuesday: Boolean = true,
    val wednesday: Boolean = true,
    val thursday: Boolean = true,
    val friday: Boolean = true,
    val saturday: Boolean = false,
    val isAddingSchedule: Boolean = false,
    val sortType: SortType = SortType.STARTTIME
)
