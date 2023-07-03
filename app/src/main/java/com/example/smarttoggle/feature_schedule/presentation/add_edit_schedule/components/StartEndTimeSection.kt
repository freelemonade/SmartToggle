package com.example.smarttoggle.feature_schedule.presentation.add_edit_schedule.components

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smarttoggle.feature_schedule.presentation.add_edit_schedule.AddEditScheduleEvent
import com.example.smarttoggle.feature_schedule.presentation.add_edit_schedule.AddEditScheduleViewModel
import java.time.LocalDateTime

@Composable
fun StartEndTimeSection(viewModel: AddEditScheduleViewModel = hiltViewModel()) {
    val startTimeState = viewModel.startTime.value
    val endTimeState = viewModel.endTime.value
    val startTime = remember { mutableStateOf("") }
    val endTime = remember { mutableStateOf("") }

    var startTimeInMin: Int = 0
    var endTimeInMin: Int = 0

    var startTimeViewState by remember { mutableStateOf(false) }
    var endTimeViewState by remember { mutableStateOf(false) }

    val dateTime = LocalDateTime.now()

    val timeStartPickerDialog = TimePickerDialog(
        LocalContext.current,
        { _, hour: Int, minute: Int ->
            var hr = ""
            var min = ""
            if (hour <= 9) hr = "0$hour" else hr = "$hour"
            if (minute <= 9) min = "0$minute" else min = "$minute"
            startTime.value = "$hr:$min"
            viewModel.onEvent(AddEditScheduleEvent.EnteredStartTime(startTime.value))
        },
        dateTime.hour,
        dateTime.minute,
        false
    )
    viewModel.onEvent(AddEditScheduleEvent.EnteredStartTime(startTimeState))
    if(startTimeState != "") {
        startTimeInMin = "${startTimeState[0]}${startTimeState[1]}".replaceFirst("^0+(?!$)", "").toInt() * 3600
        + "${startTimeState[3]}${startTimeState[4]}".replaceFirst("^0+(?!$)", "").toInt() * 60
    }


    val timeEndPickerDialog = TimePickerDialog(
        LocalContext.current,
        { _, hour: Int, minute: Int ->
            var hr = ""
            var min = ""
            if (hour <= 9) hr = "0$hour" else hr = "$hour"
            if (minute <= 9) min = "0$minute" else min = "$minute"
            endTime.value = "$hr:$min"
            viewModel.onEvent(AddEditScheduleEvent.EnteredEndTime(endTime.value))
        },
        dateTime.hour,
        dateTime.minute,
        false
    )
    viewModel.onEvent(AddEditScheduleEvent.EnteredEndTime(endTimeState))
    if(endTimeState != "") {
        endTimeInMin = "${endTimeState[0]}${endTimeState[1]}".replaceFirst("^0+(?!$)", "").toInt() * 3600
        + "${endTimeState[3]}${endTimeState[4]}".replaceFirst("^0+(?!$)", "").toInt() * 60
    }





//        var startTimeState: MutableState<TimePickerDialog> =
//            remember { mutableStateOf(timeStartPickerDialog) }
//        var endTimeState: MutableState<TimePickerDialog> =
//            remember { mutableStateOf(timeEndPickerDialog) }

    if (startTimeViewState) {
        startTimeViewState = false
        timeStartPickerDialog.show()
    }

    if (endTimeViewState) {
        endTimeViewState = false
        timeEndPickerDialog.show()
    }

    Row {
        Button(
            onClick = { startTimeViewState = true },
            shape = RoundedCornerShape(20),
            modifier = Modifier
                .weight(1f)
                .height(190.dp)
        ) {
            if (startTimeState == "") {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Start",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(5.dp))
                    Text(
                        text = "Time",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

            } else
                Text(text = startTime.value, fontSize = 40.sp)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(
            onClick = { endTimeViewState = true },
            shape = RoundedCornerShape(20),
            modifier = Modifier
                .weight(1f)
                .height(190.dp)

        ) {
            if (endTimeState == "") {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "End",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(5.dp))
                    Text(
                        text = "Time",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

            } else
                Text(text = endTime.value, fontSize = 40.sp)
        }
    }
}