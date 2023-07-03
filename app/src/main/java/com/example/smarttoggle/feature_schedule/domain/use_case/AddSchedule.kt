package com.example.smarttoggle.feature_schedule.domain.use_case

import com.example.smarttoggle.feature_schedule.domain.model.InvalidScheduleException
import com.example.smarttoggle.feature_schedule.domain.model.Schedule
import com.example.smarttoggle.feature_schedule.domain.repository.ScheduleRepository

class AddSchedule(
    private val repository: ScheduleRepository
) {
    @Throws(InvalidScheduleException::class)
    suspend operator fun invoke(schedule: Schedule) {
        if(schedule.scheduleName.isBlank()) {
            throw InvalidScheduleException("The schedule name cannot be empty")
        }
        if(!schedule.sunday && !schedule.monday && !schedule.tuesday && !schedule.wednesday && !schedule.thursday && !schedule.friday && !schedule.saturday) {
            throw InvalidScheduleException("The schedule day of week cannot be empty")
        }

        repository.insertSchedule(schedule)
    }
}