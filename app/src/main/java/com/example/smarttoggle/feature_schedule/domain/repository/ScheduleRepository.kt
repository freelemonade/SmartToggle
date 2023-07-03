package com.example.smarttoggle.feature_schedule.domain.repository

import com.example.smarttoggle.feature_schedule.domain.model.Schedule
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {

    fun getSchedules(): Flow<List<Schedule>>

    suspend fun getScheduleById(id: Int): Schedule?

    suspend fun insertSchedule(schedule: Schedule)

    suspend fun deleteSchedule(schedule: Schedule)
}