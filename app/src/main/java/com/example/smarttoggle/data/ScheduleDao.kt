package com.example.smarttoggle.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {

    @Upsert
    suspend fun upsertSchedule(schedule: Schedule)

    @Delete
    suspend fun deleteSchedule(schedule: Schedule)

    @Query("SELECT * FROM schedule_table ORDER BY startTime ASC")
    fun getScheduleOrderedByStartTime(): Flow<List<Schedule>>

    @Query("SELECT * FROM schedule_table ORDER BY endTime ASC")
    fun getScheduleOrderedByEndTime(): Flow<List<Schedule>>
}