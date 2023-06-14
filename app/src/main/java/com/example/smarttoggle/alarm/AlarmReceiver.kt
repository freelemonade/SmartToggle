package com.example.smarttoggle.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import androidx.compose.ui.platform.LocalContext
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val audioManager: AudioManager = context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return
        when(message) {
            "SILENT" -> audioManager.ringerMode = AudioManager.RINGER_MODE_SILENT
            "VIBRATE" -> audioManager.ringerMode = AudioManager.RINGER_MODE_VIBRATE
            "RING" -> audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
        }

    }
}