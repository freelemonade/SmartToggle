package com.example.smarttoggle.data

sealed interface ScheduleEvent {
    object SaveSchedule: ScheduleEvent
    data class SetScheduleName(val scheduleName: String): ScheduleEvent
    data class SetMode(val mode: String): ScheduleEvent
    data class SetStartTime(val startTime: String): ScheduleEvent
    data class SetEndTime(val endTime: String): ScheduleEvent
    data class SetSunday(val sunday: Boolean): ScheduleEvent
    data class SetMonday(val monday: Boolean): ScheduleEvent
    data class SetTuesday(val tuesday: Boolean): ScheduleEvent
    data class SetWednesday(val wednesday: Boolean): ScheduleEvent
    data class SetThursday(val thursday: Boolean): ScheduleEvent
    data class SetFriday(val friday: Boolean): ScheduleEvent
    data class SetSaturday(val saturday: Boolean): ScheduleEvent
    object ShowInput: ScheduleEvent
    object HideInput: ScheduleEvent
    data class SortSchedules(val sortType: SortType): ScheduleEvent
    data class DeleteSchedule(val schedule: Schedule): ScheduleEvent
}