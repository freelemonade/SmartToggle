package com.example.smarttoggle.feature_schedule.presentation.schedules

import com.example.smarttoggle.feature_schedule.domain.model.Schedule
import com.example.smarttoggle.feature_schedule.domain.util.ScheduleOrder

sealed class SchedulesEvent {

    data class Order(val scheduleOrder: ScheduleOrder): SchedulesEvent()
    data class DeleteSchedule(val schedule: Schedule): SchedulesEvent()
    object RestoreSchedule: SchedulesEvent()
    object ToggleOrderSection: SchedulesEvent()






//    object SaveSchedules: SchedulesEvent
//    data class SetSchedulesName(val scheduleName: String): SchedulesEvent
//    data class SetMode(val mode: String): SchedulesEvent
//    data class SetStartTime(val startTime: String): SchedulesEvent
//    data class SetEndTime(val endTime: String): SchedulesEvent
//    data class SetSunday(val sunday: Boolean): SchedulesEvent
//    data class SetMonday(val monday: Boolean): SchedulesEvent
//    data class SetTuesday(val tuesday: Boolean): SchedulesEvent
//    data class SetWednesday(val wednesday: Boolean): SchedulesEvent
//    data class SetThursday(val thursday: Boolean): SchedulesEvent
//    data class SetFriday(val friday: Boolean): SchedulesEvent
//    data class SetSaturday(val saturday: Boolean): SchedulesEvent
//    object SetDismiss: SchedulesEvent
//    object ShowInput: SchedulesEvent
//    object HideInput: SchedulesEvent
//
//    object ShowEdit: SchedulesEvent
//
//    object HideEdit: SchedulesEvent
//
//    data class SetAlarmItem(val alarmItem: AlarmItem?): SchedulesEvent
}