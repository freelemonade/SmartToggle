package com.example.smarttoggle.feature_schedule.data.repository

import com.example.smarttoggle.feature_schedule.data.data_source.ScheduleDao
import com.example.smarttoggle.feature_schedule.domain.model.Schedule
import com.example.smarttoggle.feature_schedule.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class ScheduleRepositoryImpl(
    private val dao: ScheduleDao
): ScheduleRepository {
    override fun getSchedules(): Flow<List<Schedule>> {
        return dao.getSchedules()
    }

    override suspend fun getScheduleById(id: Int): Schedule? {
        return dao.getScheduleById(id)
    }

    override suspend fun insertSchedule(schedule: Schedule) {
        dao.insertSchedule(schedule)
    }

    override suspend fun deleteSchedule(schedule: Schedule) {
        dao.deleteSchedule(schedule)
    }
}