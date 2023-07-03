package com.example.smarttoggle.feature_schedule.presentation.add_edit_schedule.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smarttoggle.R
import com.example.smarttoggle.feature_schedule.presentation.add_edit_schedule.AddEditScheduleEvent
import com.example.smarttoggle.feature_schedule.presentation.add_edit_schedule.AddEditScheduleViewModel

@Composable
fun RingerModeSelectionSection(viewModel: AddEditScheduleViewModel = hiltViewModel()) {
    Row {
        var selectedSilent by remember { mutableStateOf(false) }
        var selectedVibrate by remember { mutableStateOf(false) }
        var selectedNormal by remember { mutableStateOf(false) }


        val buttonColorSilent =
            if (selectedSilent) Color.Green else Color(R.color.androidgreen)
        val buttonColorVibrate =
            if (selectedVibrate) Color.Green else Color(R.color.androidgreen)
        val buttonColorNormal =
            if (selectedNormal) Color.Green else Color(R.color.androidgreen)

        Button(
            onClick = {
                viewModel.onEvent(AddEditScheduleEvent.ChangedRingerType("silent"))
                selectedSilent = false
                selectedVibrate = false
                selectedNormal = false
                selectedSilent = !selectedSilent
            },
            shape = RoundedCornerShape(40),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColorSilent),
            modifier = Modifier.weight(1f).height(60.dp)
        ) {
            Text(text = "Silent", fontSize = 15.sp)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(
            onClick = {
                viewModel.onEvent(AddEditScheduleEvent.ChangedRingerType("vibrate"))
                selectedSilent = false
                selectedVibrate = false
                selectedNormal = false
                selectedVibrate = !selectedVibrate
            },
            shape = RoundedCornerShape(40),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColorVibrate),
            modifier = Modifier.weight(1f).height(60.dp)
        ) {
            Text(text = "Vibrate", fontSize = 15.sp)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(
            onClick = {
                viewModel.onEvent(AddEditScheduleEvent.ChangedRingerType("normal"))
                selectedSilent = false
                selectedVibrate = false
                selectedNormal = false
                selectedNormal = !selectedNormal
            },
            shape = RoundedCornerShape(40),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColorNormal),
            modifier = Modifier.weight(1f).height(60.dp)
        ) {
            Text(text = "Ring", fontSize = 15.sp)
        }
    }
}