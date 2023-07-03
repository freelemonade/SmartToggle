package com.example.smarttoggle

interface AlarmScheduler {
    fun schedule(item: AlarmItem, dayOfWeek: Int)
    fun cancel(item: AlarmItem)
}