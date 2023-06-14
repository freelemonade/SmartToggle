package com.example.smarttoggle.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule_table")
data class Schedule(
    val scheduleName: String?,
    val mode: String?,
    val startTime: String?,
    val endTime: String?,
    val sunday: Boolean,
    val monday: Boolean,
    val tuesday: Boolean,
    val wednesday: Boolean,
    val thursday: Boolean,
    val friday: Boolean,
    val saturday: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
