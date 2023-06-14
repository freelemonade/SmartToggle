package com.example.smarttoggle.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Schedule::class], version = 1)
abstract class ScheduleDatabase: RoomDatabase() {
    abstract val dao: ScheduleDao
}