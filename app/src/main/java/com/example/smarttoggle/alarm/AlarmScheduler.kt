package com.example.smarttoggle.alarm

interface AlarmScheduler {
    fun schedule(item: AlarmItem, dayOfWeek: Int)
    fun cancel(item: AlarmItem)
}