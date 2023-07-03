package com.example.smarttoggle.feature_schedule.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.smarttoggle.Converters
import com.example.smarttoggle.feature_schedule.domain.model.Schedule

@Database(entities = [Schedule::class], version = 1)
@TypeConverters(Converters::class)
abstract class ScheduleDatabase: RoomDatabase() {
    abstract val scheduleDao: ScheduleDao

    companion object {
        const val DATABASE_NAME = "schedules_db"
    }
}