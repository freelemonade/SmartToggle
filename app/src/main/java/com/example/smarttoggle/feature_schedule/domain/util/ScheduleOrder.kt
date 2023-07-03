package com.example.smarttoggle.feature_schedule.domain.util

sealed class ScheduleOrder(val orderType: OrderType) {
    class ScheduleName(orderType: OrderType): ScheduleOrder(orderType)
    class StartTime(orderType: OrderType): ScheduleOrder(orderType)
    class Color(orderType: OrderType): ScheduleOrder(orderType)

    fun copy(orderType: OrderType): ScheduleOrder {
        return when(this) {
            is ScheduleName -> StartTime(orderType)
            is StartTime -> StartTime(orderType)
            is Color -> Color(orderType)
        }
    }
}
