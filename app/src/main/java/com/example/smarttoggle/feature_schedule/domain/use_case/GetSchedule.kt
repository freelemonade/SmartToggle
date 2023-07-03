package com.example.smarttoggle.feature_schedule.domain.use_case

import com.example.smarttoggle.feature_schedule.domain.model.Schedule
import com.example.smarttoggle.feature_schedule.domain.repository.ScheduleRepository

class GetSchedule(
    private val repository: ScheduleRepository
) {

    suspend operator fun invoke(id: Int): Schedule? {
        return repository.getScheduleById(id)
    }
}