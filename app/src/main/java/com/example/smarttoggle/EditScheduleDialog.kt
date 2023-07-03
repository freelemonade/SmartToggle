//package com.example.smarttoggle
//
//
//import android.app.TimePickerDialog
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.AlertDialog
//import androidx.compose.material3.AlertDialogDefaults
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.MutableState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.ExperimentalComposeUiApi
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.smarttoggle.feature_schedule.domain.model.Schedule
//import com.example.smarttoggle.feature_schedule.presentation.schedules.SchedulesEvent
//import com.example.smarttoggle.feature_schedule.presentation.schedules.SchedulesState
//import java.time.DayOfWeek
//import java.time.LocalDate
//import java.time.LocalDateTime
//import java.time.temporal.TemporalAdjusters
//import java.util.Calendar
//
//@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
//@Composable
//fun EditScheduleDialog(
//    state: SchedulesState,
//    onEvent: (SchedulesEvent) -> Unit,
//    schedule: Schedule?,
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
//        onDismissRequest = { onEvent(SchedulesEvent.HideEdit) }
//    ) {
//        Surface(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(480.dp),
//            shape = MaterialTheme.shapes.large,
//            tonalElevation = AlertDialogDefaults.TonalElevation,
//
//            ) {
//            Column(
//                verticalArrangement = Arrangement.spacedBy(8.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = modifier.padding(8.dp)
//
//            ) {
//                OutlinedTextField(
//                    value = state.scheduleName,
//                    onValueChange = {
//                        onEvent(SchedulesEvent.SetSchedulesName(it))
//                    },
//                    label = {
//                        Text(text = "Schedule name")
//                    },
//                    singleLine = true,
//                )
//                Row {
//                    var selectedSilent by remember { mutableStateOf(false) }
//                    var selectedVibrate by remember { mutableStateOf(false) }
//                    var selectedRing by remember { mutableStateOf(false) }
//
//
//                    val buttonColorSilent =
//                        if (selectedSilent) Color.Green else Color(R.color.androidgreen)
//                    val buttonColorVibrate =
//                        if (selectedVibrate) Color.Green else Color(R.color.androidgreen)
//                    val buttonColorRing =
//                        if (selectedRing) Color.Green else Color(R.color.androidgreen)
//
//                    Button(
//                        onClick = {
//                            onEvent(SchedulesEvent.SetMode("SILENT"))
//                            selectedSilent = false
//                            selectedVibrate = false
//                            selectedRing = false
//                            selectedSilent = !selectedSilent
//                        },
//                        shape = RoundedCornerShape(40),
//                        colors = ButtonDefaults.buttonColors(containerColor = buttonColorSilent),
//                        modifier = modifier.weight(1f)
//                    ) {
//                        Text("Silent")
//                    }
//                    Spacer(modifier = modifier.width(10.dp))
//                    Button(
//                        onClick = {
//                            onEvent(SchedulesEvent.SetMode("VIBRATE"))
//                            selectedSilent = false
//                            selectedVibrate = false
//                            selectedRing = false
//                            selectedVibrate = !selectedVibrate
//                        },
//                        shape = RoundedCornerShape(40),
//                        colors = ButtonDefaults.buttonColors(containerColor = buttonColorVibrate),
//                        modifier = modifier.weight(1f)
//                    ) {
//                        Text("Vibrate")
//                    }
//                    Spacer(modifier = modifier.width(10.dp))
//                    Button(
//                        onClick = {
//                            onEvent(SchedulesEvent.SetMode("RING"))
//                            selectedSilent = false
//                            selectedVibrate = false
//                            selectedRing = false
//                            selectedRing = !selectedRing
//                        },
//                        shape = RoundedCornerShape(40),
//                        colors = ButtonDefaults.buttonColors(containerColor = buttonColorRing),
//                        modifier = modifier.weight(1f)
//                    ) {
//                        Text("Ring")
//                    }
//                }
//
//
//
//                var startTimeViewState by remember { mutableStateOf(false) }
//                var endTimeViewState by remember { mutableStateOf(false) }
//
//                val dateTime = LocalDateTime.now()
//
//
//                val timeStartPickerDialog = TimePickerDialog(
//                    LocalContext.current,
//                    { _, hour: Int, minute: Int ->
//                        var hr = ""
//                        var min = ""
//                        if (hour <= 9) hr = "0$hour" else hr = "$hour"
//                        if (minute <= 9) min = "0$minute" else min = "$minute"
//                        startTime.value = "$hr:$min"
//                    },
//                    dateTime.hour,
//                    dateTime.minute,
//                    false
//                )
//
//                onEvent(SchedulesEvent.SetStartTime(startTime.value))
//
//
//                if(startTime.value != "") {
//                    startTimeInMin = "${startTime.value[0]}${startTime.value[1]}".replaceFirst("^0+(?!$)", "").toInt() * 3600 + "${startTime.value[3]}${startTime.value[4]}".replaceFirst("^0+(?!$)", "").toInt() * 60
//                }
//
//                val timeEndPickerDialog = TimePickerDialog(
//                    LocalContext.current,
//                    { _, hour: Int, minute: Int ->
//                        var hr = ""
//                        var min = ""
//                        if (hour <= 9) hr = "0$hour" else hr = "$hour"
//                        if (minute <= 9) min = "0$minute" else min = "$minute"
//                        endTime.value = "$hr:$min"
//                    },
//                    dateTime.hour,
//                    dateTime.minute,
//                    false
//                )
//                onEvent(SchedulesEvent.SetEndTime(endTime.value))
//                if(endTime.value != "") {
//                    endTimeInMin = "${endTime.value[0]}${endTime.value[1]}".replaceFirst("^0+(?!$)", "").toInt() * 3600 + "${endTime.value[3]}${endTime.value[4]}".replaceFirst("^0+(?!$)", "").toInt() * 60
//                }
//
//                var startTimeState: MutableState<TimePickerDialog> =
//                    remember { mutableStateOf(timeStartPickerDialog) }
//                var endTimeState: MutableState<TimePickerDialog> =
//                    remember { mutableStateOf(timeEndPickerDialog) }
//
//                if (startTimeViewState) {
//                    startTimeViewState = false
//                    timeStartPickerDialog.show()
//                }
//
//                if (endTimeViewState) {
//                    endTimeViewState = false
//                    timeEndPickerDialog.show()
//                }
//
//                Button(
//                    onClick = { startTimeViewState = true },
//                    shape = RoundedCornerShape(20),
//                    modifier = modifier
//                        .fillMaxWidth()
//                        .height(90.dp)
//                ) {
//                    if(startTime.value == "") {
//                        Text(
//                            text = schedule?.startTime.toString(),
//                            fontSize = 40.sp,
//                            fontWeight = FontWeight.Bold,
//                            textAlign = TextAlign.Center
//                        )
//                    } else {
//                        Text(text = startTime.value, fontSize = 40.sp)
//                    }
//                }
//                Spacer(modifier = Modifier.height(0.dp))
//                Button(
//                    onClick = { endTimeViewState = true },
//                    shape = RoundedCornerShape(20),
//                    modifier = modifier
//                        .fillMaxWidth()
//                        .height(90.dp)
//
//                ) {
//                    if (endTime.value == "") {
//                        Text(
//                            text = schedule?.endTime.toString(),
//                            fontSize = 40.sp,
//                            fontWeight = FontWeight.Bold,
//                            textAlign = TextAlign.Center
//                        )
//
//                    } else Text(text = endTime.value, fontSize = 40.sp)
//                }
//                Spacer(modifier = Modifier.height(0.dp))
//
//
//                Row {
//                    var selectedSu by remember { mutableStateOf(schedule!!.sunday) }
//                    var selectedM by remember { mutableStateOf(schedule!!.monday) }
//                    var selectedTu by remember { mutableStateOf(schedule!!.tuesday) }
//                    var selectedW by remember { mutableStateOf(schedule!!.wednesday) }
//                    var selectedTh by remember { mutableStateOf(schedule!!.thursday) }
//                    var selectedF by remember { mutableStateOf(schedule!!.friday) }
//                    var selectedSa by remember { mutableStateOf(schedule!!.saturday) }
//
//                    val buttonColorSu =
//                        if (selectedSu) Color.Green else Color(R.color.androidgreen)
//                    val buttonColorM =
//                        if (selectedM) Color.Green else Color(R.color.androidgreen)
//                    val buttonColorTu =
//                        if (selectedTu) Color.Green else Color(R.color.androidgreen)
//                    val buttonColorW =
//                        if (selectedW) Color.Green else Color(R.color.androidgreen)
//                    val buttonColorTh =
//                        if (selectedTh) Color.Green else Color(R.color.androidgreen)
//                    val buttonColorF =
//                        if (selectedF) Color.Green else Color(R.color.androidgreen)
//                    val buttonColorSa =
//                        if (selectedSa) Color.Green else Color(R.color.androidgreen)
//
//
//                    TextButton(
//                        onClick = { onEvent(SchedulesEvent.SetSunday(!selectedSu))
//                            selectedSu = !selectedSu },
//                        modifier = modifier.size(40.dp),
//                        shape = CircleShape,
//                        colors = ButtonDefaults.buttonColors(containerColor = buttonColorSu))
//                    {
//                        Text("Su")
//                    }
//                    Spacer(modifier = Modifier.width(4.dp))
//                    TextButton(
//                        onClick = { onEvent(SchedulesEvent.SetMonday(!selectedM))
//                            selectedM = !selectedM },
//                        modifier = modifier.size(40.dp),
//                        shape = CircleShape,
//                        colors = ButtonDefaults.buttonColors(containerColor = buttonColorM))
//                    {
//                        Text("M")
//                    }
//                    Spacer(modifier = Modifier.width(4.dp))
//                    TextButton(
//                        onClick = { onEvent(SchedulesEvent.SetTuesday(!selectedTu))
//                            selectedTu = !selectedTu },
//                        modifier = modifier.size(40.dp),
//                        shape = CircleShape,
//                        colors = ButtonDefaults.buttonColors(containerColor = buttonColorTu))
//                    {
//                        Text("Tu")
//                    }
//                    Spacer(modifier = Modifier.width(4.dp))
//                    TextButton(
//                        onClick = { onEvent(SchedulesEvent.SetWednesday(!selectedW))
//                            selectedW = !selectedW },
//                        modifier = modifier.size(40.dp),
//                        shape = CircleShape,
//                        colors = ButtonDefaults.buttonColors(containerColor = buttonColorW))
//                    {
//                        Text("W")
//                    }
//                    Spacer(modifier = Modifier.width(4.dp))
//                    TextButton(
//                        onClick = { onEvent(SchedulesEvent.SetThursday(!selectedTh))
//                            selectedTh = !selectedTh },
//                        modifier = modifier.size(40.dp),
//                        shape = CircleShape,
//                        colors = ButtonDefaults.buttonColors(containerColor = buttonColorTh))
//                    {
//                        Text("Th")
//                    }
//                    Spacer(modifier = Modifier.width(4.dp))
//                    TextButton(
//                        onClick = { onEvent(SchedulesEvent.SetFriday(!selectedF))
//                            selectedF = !selectedF },
//                        modifier = modifier.size(40.dp),
//                        shape = CircleShape,
//                        colors = ButtonDefaults.buttonColors(containerColor = buttonColorF))
//                    {
//                        Text("F")
//                    }
//                    Spacer(modifier = Modifier.width(4.dp))
//                    TextButton(
//                        onClick = { onEvent(SchedulesEvent.SetSaturday(!selectedSa))
//                            selectedSa = !selectedSa },
//                        modifier = modifier.size(40.dp),
//                        shape = CircleShape,
//                        colors = ButtonDefaults.buttonColors(containerColor = buttonColorSa))
//                    {
//                        Text("Sa")
//                    }
//                }
//
//
//                Spacer(modifier = Modifier.height(0.dp))
//                Button(onClick = {
//
//
//
//                    fun scheduleAlarm(nextOrCurrentDayOfWeek: LocalDate) {
//                        if(state.startTime != "" && state.endTime != "") {
//                            val year = nextOrCurrentDayOfWeek.year
//                            val month = nextOrCurrentDayOfWeek.month
//                            val dayOfMonth = nextOrCurrentDayOfWeek.dayOfMonth
//                            var hour = (state.startTime[0].toString() + state.startTime[1].toString()).toInt()
//                            var minute = (state.startTime[3].toString() + state.startTime[4].toString()).toInt()
//                            println("KERE ${year} ${month} ${dayOfMonth} ${hour} ${minute}")
//                            val alarmItemStartTime = AlarmItem(
//                                time = LocalDateTime.of(year,month,dayOfMonth,hour,minute),
//                                message = state.mode
//                            )
//                            //alarmItem?.let(scheduler::schedule)
//                            scheduler.schedule(alarmItemStartTime!!, Calendar.DAY_OF_WEEK)
//
//
//
//                            hour = (state.endTime[0].toString() + state.endTime[1].toString()).toInt()
//                            minute = (state.endTime[3].toString() + state.endTime[4].toString()).toInt()
//
//
//
//                            alarmItemEndTime = AlarmItem(
//                                time = LocalDateTime.of(year,month,dayOfMonth,hour,minute),
//                                message = ""
//                            )
//                            scheduler.schedule(alarmItemEndTime!!, Calendar.DAY_OF_WEEK)
//                        }
//                    }
//
//                    val dateNow: LocalDate = LocalDate.now()
//                    val days: MutableList<Boolean> = mutableListOf()
//                    days.add(state.sunday)
//                    days.add(state.monday)
//                    days.add(state.tuesday)
//                    days.add(state.wednesday)
//                    days.add(state.thursday)
//                    days.add(state.friday)
//                    days.add(state.saturday)
//                    days.forEachIndexed {index, day ->
//                        if(day) {
//                            when(index) {
//                                0 -> {
//                                    val nextOrCurrentDayOfWeek = dateNow.with(
//                                        TemporalAdjusters.nextOrSame(
//                                            DayOfWeek.SUNDAY))
//                                    scheduleAlarm(nextOrCurrentDayOfWeek)
//                                }
//                                1 -> {
//                                    val nextOrCurrentDayOfWeek = dateNow.with(
//                                        TemporalAdjusters.nextOrSame(
//                                            DayOfWeek.MONDAY))
//                                    scheduleAlarm(nextOrCurrentDayOfWeek)
//                                }
//                                2 -> {
//                                    val nextOrCurrentDayOfWeek = dateNow.with(
//                                        TemporalAdjusters.nextOrSame(
//                                            DayOfWeek.TUESDAY))
//                                    scheduleAlarm(nextOrCurrentDayOfWeek)
//                                }
//                                3 -> {
//                                    val nextOrCurrentDayOfWeek = dateNow.with(
//                                        TemporalAdjusters.nextOrSame(
//                                            DayOfWeek.WEDNESDAY))
//                                    scheduleAlarm(nextOrCurrentDayOfWeek)
//                                }
//                                4 -> {
//                                    val nextOrCurrentDayOfWeek = dateNow.with(
//                                        TemporalAdjusters.nextOrSame(
//                                            DayOfWeek.THURSDAY))
//                                    scheduleAlarm(nextOrCurrentDayOfWeek)
//                                }
//                                5 -> {
//                                    val nextOrCurrentDayOfWeek = dateNow.with(
//                                        TemporalAdjusters.nextOrSame(
//                                            DayOfWeek.FRIDAY))
//                                    scheduleAlarm(nextOrCurrentDayOfWeek)
//                                }
//                                6 -> {
//                                    val nextOrCurrentDayOfWeek = dateNow.with(
//                                        TemporalAdjusters.nextOrSame(
//                                            DayOfWeek.SATURDAY))
//                                    scheduleAlarm(nextOrCurrentDayOfWeek)
//                                }
//                            }
//                        }
//                    }
//                    onEvent(SchedulesEvent.SetAlarmItem(alarmItemStartTime))
//                    onEvent(SchedulesEvent.SetAlarmItem(alarmItemEndTime))
//                    onEvent(SchedulesEvent.SaveSchedules)
//
//                }) {
//                    //val alarm: AlarmReceiver? = null
//                    //alarm?.setAlarm(LocalContext.current, alarmItemStartTime, state.mode)
//                    Text(
//                        text = "Save",
//                        modifier = modifier.fillMaxWidth(),
//                        textAlign = TextAlign.Center,
//                        fontSize = 20.sp
//                    )
//                }
//
//            }
//        }
//    }
//    return Pair(alarmItemStartTime, alarmItemEndTime)
//}