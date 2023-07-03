package com.example.smarttoggle.feature_schedule.presentation.add_edit_schedule

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smarttoggle.AlarmItem
import com.example.smarttoggle.R
import com.example.smarttoggle.feature_schedule.domain.model.Schedule
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters
import java.util.Calendar
import androidx.compose.material.*
import androidx.compose.material3.TextFieldColors
import com.example.smarttoggle.AndroidAlarmScheduler
import com.example.smarttoggle.feature_schedule.presentation.add_edit_schedule.components.DayOfWeekSelectionSection
import com.example.smarttoggle.feature_schedule.presentation.add_edit_schedule.components.RingerModeSelectionSection
import com.example.smarttoggle.feature_schedule.presentation.add_edit_schedule.components.StartEndTimeSection

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditScheduleScreen(
    navController: NavController,
    scheduleColor: Int,
    viewModel: AddEditScheduleViewModel = hiltViewModel()
) {
    val scheduler = AndroidAlarmScheduler(LocalContext.current)

    val scheduleNameState = viewModel.scheduleName.value.text
    val ringerTypeState = viewModel.ringerType.value
    val startTimeState = viewModel.startTime.value
    val endTimeState = viewModel.endTime.value
    val sundayState = viewModel.sunday.value
    val mondayState = viewModel.monday.value
    val tuesdayState = viewModel.tuesday.value
    val wednesdayState = viewModel.wednesday.value
    val thursdayState = viewModel.thursday.value
    val fridayState = viewModel.friday.value
    val saturdayState = viewModel.saturday.value

    val snackbarHostState = remember { SnackbarHostState() }

    val scheduleBackgroundAnimatable = remember {
        Animatable(
            Color(if(scheduleColor != -1) scheduleColor else viewModel.scheduleColor.value)
        )
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditScheduleViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditScheduleViewModel.UiEvent.SaveSchedule -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(scheduleBackgroundAnimatable.value)
                .padding(top = 30.dp)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Schedule.scheduleColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.scheduleColor.value == colorInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    scheduleBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(durationMillis = 500)
                                    )
                                }
                                viewModel.onEvent(AddEditScheduleEvent.ChangeColor(colorInt))
                            }
                    )
                }
            }

            OutlinedTextField(
                value = scheduleNameState,
                onValueChange = {
                    viewModel.onEvent(AddEditScheduleEvent.EnteredScheduleName(it))
                },
                label = {
                    Text(text = "Schedule Name",
                        color = Color.Black)
                        },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            RingerModeSelectionSection(viewModel)

            StartEndTimeSection(viewModel)

            DayOfWeekSelectionSection(viewModel)

            Button(onClick = {

                fun scheduleAlarm(nextOrCurrentDayOfWeek: LocalDate) {


                    if(startTimeState != "" && endTimeState != "") {
                        val year = nextOrCurrentDayOfWeek.year
                        val month = nextOrCurrentDayOfWeek.month
                        val dayOfMonth = nextOrCurrentDayOfWeek.dayOfMonth
                        var hour = (startTimeState[0].toString() + startTimeState[1].toString()).toInt()
                        var minute = (startTimeState[3].toString() + startTimeState[4].toString()).toInt()
                        println("KERE ${year} ${month} ${dayOfMonth} ${hour} ${minute}")
                        val alarmItemStartTime = AlarmItem(
                            time = LocalDateTime.of(year,month,dayOfMonth,hour,minute),
                            ringerType = ringerTypeState
                        )
                        //alarmItem?.let(scheduler::schedule)
                        scheduler.schedule(alarmItemStartTime, Calendar.DAY_OF_WEEK)



                        hour = (endTimeState[0].toString() + endTimeState[1].toString()).toInt()
                        minute = (endTimeState[3].toString() + endTimeState[4].toString()).toInt()



                        val alarmItemEndTime = AlarmItem(
                            time = LocalDateTime.of(year,month,dayOfMonth,hour,minute),
                            ringerType = ""
                        )
                        scheduler.schedule(alarmItemEndTime, Calendar.DAY_OF_WEEK)
                    }
                }

                val dateNow: LocalDate = LocalDate.now()
                val days: MutableList<Boolean> = mutableListOf()
                days.add(sundayState)
                days.add(mondayState)
                days.add(tuesdayState)
                days.add(wednesdayState)
                days.add(thursdayState)
                days.add(fridayState)
                days.add(saturdayState)
                days.forEachIndexed {index, day ->
                    if(day) {
                        when(index) {
                            0 -> {
                                val nextOrCurrentDayOfWeek = dateNow.with(
                                    TemporalAdjusters.nextOrSame(
                                        DayOfWeek.SUNDAY))
                                scheduleAlarm(nextOrCurrentDayOfWeek)
                            }
                            1 -> {
                                val nextOrCurrentDayOfWeek = dateNow.with(
                                    TemporalAdjusters.nextOrSame(
                                        DayOfWeek.MONDAY))
                                scheduleAlarm(nextOrCurrentDayOfWeek)
                            }
                            2 -> {
                                val nextOrCurrentDayOfWeek = dateNow.with(
                                    TemporalAdjusters.nextOrSame(
                                        DayOfWeek.TUESDAY))
                                scheduleAlarm(nextOrCurrentDayOfWeek)
                            }
                            3 -> {
                                val nextOrCurrentDayOfWeek = dateNow.with(
                                    TemporalAdjusters.nextOrSame(
                                        DayOfWeek.WEDNESDAY))
                                scheduleAlarm(nextOrCurrentDayOfWeek)
                            }
                            4 -> {
                                val nextOrCurrentDayOfWeek = dateNow.with(
                                    TemporalAdjusters.nextOrSame(
                                        DayOfWeek.THURSDAY))
                                scheduleAlarm(nextOrCurrentDayOfWeek)
                            }
                            5 -> {
                                val nextOrCurrentDayOfWeek = dateNow.with(
                                    TemporalAdjusters.nextOrSame(
                                        DayOfWeek.FRIDAY))
                                scheduleAlarm(nextOrCurrentDayOfWeek)
                            }
                            6 -> {
                                val nextOrCurrentDayOfWeek = dateNow.with(
                                    TemporalAdjusters.nextOrSame(
                                        DayOfWeek.SATURDAY))
                                scheduleAlarm(nextOrCurrentDayOfWeek)
                            }
                        }
                    }
                }
                viewModel.onEvent(AddEditScheduleEvent.EnteredStartTime(startTimeState))
                viewModel.onEvent(AddEditScheduleEvent.EnteredEndTime(endTimeState))
                viewModel.onEvent(AddEditScheduleEvent.SaveSchedule)

            }) {
                //val alarm: AlarmReceiver? = null
                //alarm?.setAlarm(LocalContext.current, alarmItemStartTime, state.mode)
                Text(
                    text = "Save",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp
                )
            }
    }

    }

}