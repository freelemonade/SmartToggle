package com.example.smarttoggle.feature_schedule.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.smarttoggle.ui.theme.BabyBlue
import com.example.smarttoggle.ui.theme.LightGreen
import com.example.smarttoggle.ui.theme.RedOrange
import com.example.smarttoggle.ui.theme.RedPink
import com.example.smarttoggle.ui.theme.Violet
import java.lang.Exception

@Entity(tableName = "schedule_table")
data class Schedule(
    val color: Int,
    val scheduleName: String,
    val ringerType: String,
    val startTime: String,
    val endTime: String,
    val sunday: Boolean,
    val monday: Boolean,
    val tuesday: Boolean,
    val wednesday: Boolean,
    val thursday: Boolean,
    val friday: Boolean,
    val saturday: Boolean,
    //val alarmItem: AlarmItem,
    @PrimaryKey
    val id: Int? = null
) {
    companion object {
        val scheduleColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }

}

class InvalidScheduleException(message: String): Exception(message)
