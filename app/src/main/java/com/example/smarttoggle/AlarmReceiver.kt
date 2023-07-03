package com.example.smarttoggle

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import androidx.compose.ui.platform.LocalContext
import java.io.Serializable
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val audioManager: AudioManager = context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val ringerType = intent?.getStringExtra("EXTRA_RINGERTYPE") ?: return

        //setAlarm(context, alarmItem, message)
        println("here: ${ringerType}")
        when(ringerType) {
            "silent" -> audioManager.ringerMode = AudioManager.RINGER_MODE_SILENT
            "vibrate" -> audioManager.ringerMode = AudioManager.RINGER_MODE_VIBRATE
            "normal" -> audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
        }

    }

//    fun setAlarm(context: Context?, alarmItem:AlarmItem?, message: String) {
//        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(context, AlarmReceiver::class.java).apply {
//            putExtra("EXTRA_MESSAGE", message)
//        }
//        alarmManager.setExact(
//            AlarmManager.RTC_WAKEUP,
//            item.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
//            PendingIntent.getBroadcast(
//                context,
//                0,
//                intent,
//                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//            )
//        )
//    }
}