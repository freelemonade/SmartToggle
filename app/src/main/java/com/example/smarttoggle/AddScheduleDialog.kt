package com.example.smarttoggle

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarttoggle.feature_schedule.presentation.schedules.SchedulesEvent
import com.example.smarttoggle.feature_schedule.presentation.schedules.SchedulesState
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters
import java.util.Calendar

//@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
//@Composable
//fun AddScheduleDialog(
//    state: SchedulesState,
//    onEvent: (SchedulesEvent) -> Unit,
//    modifier: Modifier = Modifier
//): Pair<AlarmItem?, AlarmItem?> {
//    val startTime = remember { mutableStateOf("") }
//    val endTime = remember { mutableStateOf("") }
//    var startTimeInMin = 0
//    var endTimeInMin = 0
//    var alarmItemStartTime: AlarmItem? = null
//    var alarmItemEndTime: AlarmItem? = null
//    val scheduler = AndroidAlarmScheduler(LocalContext.current)
//
//
//    AlertDialog(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(480.dp),
//        onDismissRequest = { onEvent(SchedulesEvent.HideInput) }
//        ) {
//        Surface(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(480.dp),
//            shape = MaterialTheme.shapes.large,
//            tonalElevation = AlertDialogDefaults.TonalElevation,
//
//        ) {
//
//        }
//    }
//    return Pair(alarmItemStartTime, alarmItemEndTime)
//}