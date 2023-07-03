package com.example.smarttoggle.feature_schedule.domain.use_case

import androidx.room.Index
import com.example.smarttoggle.feature_schedule.domain.model.Schedule
import com.example.smarttoggle.feature_schedule.domain.repository.ScheduleRepository
import com.example.smarttoggle.feature_schedule.domain.util.OrderType
import com.example.smarttoggle.feature_schedule.domain.util.ScheduleOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSchedules(
    private val repository: ScheduleRepository
) {
    operator fun invoke(
        scheduleOrder: ScheduleOrder = ScheduleOrder.StartTime(OrderType.Descending)
    ): Flow<List<Schedule>> {
        return repository.getSchedules().map { schedules ->
            when(scheduleOrder.orderType) {
                is OrderType.Ascending -> {
                    when(scheduleOrder) {
                        is ScheduleOrder.ScheduleName -> schedules.sortedBy { it.scheduleName.lowercase() }
                        is ScheduleOrder.StartTime -> schedules.sortedBy { it.startTime }
                        is ScheduleOrder.Color -> schedules.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when(scheduleOrder) {
                        is ScheduleOrder.ScheduleName -> schedules.sortedByDescending { it.scheduleName.lowercase() }
                        is ScheduleOrder.StartTime -> schedules.sortedByDescending { it.startTime }
                        is ScheduleOrder.Color -> schedules.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}