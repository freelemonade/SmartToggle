package com.example.smarttoggle.feature_schedule.presentation.schedules

import com.example.smarttoggle.feature_schedule.domain.model.Schedule
import com.example.smarttoggle.feature_schedule.domain.util.OrderType
import com.example.smarttoggle.feature_schedule.domain.util.ScheduleOrder
import java.time.LocalDateTime

data class SchedulesState(
    val schedules: List<Schedule> = emptyList(),
    val scheduleOrder: ScheduleOrder = ScheduleOrder.StartTime(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,




//    val scheduleName: String = "",
//    val mode: RingerType = RingerType.Normal,
//    val startTime: String = "",
//    val endTime: String = "",
//    val sunday: Boolean = false,
//    val monday: Boolean = true,
//    val tuesday: Boolean = true,
//    val wednesday: Boolean = true,
//    val thursday: Boolean = true,
//    val friday: Boolean = true,
//    val saturday: Boolean = false,
//    val isAddingSchedule: Boolean = false,
//    val isEditingSchedule: Boolean = false,
//    val isDismissed: Boolean = false,
//    val alarmItem: AlarmItem? = AlarmItem(LocalDateTime.now(), "")
)
