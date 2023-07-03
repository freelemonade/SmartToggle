package com.example.smarttoggle.feature_schedule.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.smarttoggle.feature_schedule.domain.model.Schedule
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule: Schedule)

    @Delete
    suspend fun deleteSchedule(schedule: Schedule)

    @Query("SELECT * FROM schedule_table")
    fun getSchedules(): Flow<List<Schedule>>

    @Query("SELECT * FROM schedule_table WHERE id = :id")
    fun getScheduleById(id: Int): Schedule?
}