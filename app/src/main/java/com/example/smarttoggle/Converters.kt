package com.example.smarttoggle

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {
    @TypeConverter
    fun stringToAlarmItem(value: String): AlarmItem? {
        val dateTimeValue = value.substringBefore(" ")
        val messageValue = value.substringAfter(" ")
        var timeValue = LocalDateTime.now()
        if(dateTimeValue.isNullOrEmpty()) {
            timeValue = LocalDateTime.parse(dateTimeValue, DateTimeFormatter.ISO_DATE_TIME)
        }

        val alarmItem: AlarmItem?
        alarmItem = AlarmItem(
            time = timeValue,
            ringerType = messageValue
        )

        return alarmItem
    }

    @TypeConverter
    fun alarmItemToString(alarmItem: AlarmItem?): String {
        return "${alarmItem?.time} ${alarmItem?.ringerType}"
    }

}