package com.example.smarttoggle.feature_schedule.presentation.add_edit_schedule.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smarttoggle.R
import com.example.smarttoggle.feature_schedule.presentation.add_edit_schedule.AddEditScheduleEvent
import com.example.smarttoggle.feature_schedule.presentation.add_edit_schedule.AddEditScheduleViewModel

@Composable
fun DayOfWeekSelectionSection(viewModel: AddEditScheduleViewModel = hiltViewModel()) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        var selectedSu by remember { mutableStateOf(false) }
        var selectedM by remember { mutableStateOf(true) }
        var selectedTu by remember { mutableStateOf(true) }
        var selectedW by remember { mutableStateOf(true) }
        var selectedTh by remember { mutableStateOf(true) }
        var selectedF by remember { mutableStateOf(true) }
        var selectedSa by remember { mutableStateOf(false) }

        val buttonColorSu =
            if (selectedSu) Color.Green else Color(R.color.androidgreen)
        val buttonColorM =
            if (selectedM) Color.Green else Color(R.color.androidgreen)
        val buttonColorTu =
            if (selectedTu) Color.Green else Color(R.color.androidgreen)
        val buttonColorW =
            if (selectedW) Color.Green else Color(R.color.androidgreen)
        val buttonColorTh =
            if (selectedTh) Color.Green else Color(R.color.androidgreen)
        val buttonColorF =
            if (selectedF) Color.Green else Color(R.color.androidgreen)
        val buttonColorSa =
            if (selectedSa) Color.Green else Color(R.color.androidgreen)


        TextButton(
            onClick = { viewModel.onEvent(AddEditScheduleEvent.EnteredSunday(!selectedSu))
                selectedSu = !selectedSu },
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = buttonColorSu))
        {
            Text(
                text = "Su",
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        TextButton(
            onClick = { viewModel.onEvent(AddEditScheduleEvent.EnteredMonday(!selectedM))
                selectedM = !selectedM },
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = buttonColorM))
        {
            Text(
                text = "M",
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        TextButton(
            onClick = { viewModel.onEvent(AddEditScheduleEvent.EnteredTuesday(!selectedTu))
                selectedTu = !selectedTu },
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = buttonColorTu))
        {
            Text(
                text = "Tu",
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        TextButton(
            onClick = { viewModel.onEvent(AddEditScheduleEvent.EnteredWednesday(!selectedW))
                selectedW = !selectedW },
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = buttonColorW))
        {
            Text(
                text = "W",
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        TextButton(
            onClick = { viewModel.onEvent(AddEditScheduleEvent.EnteredThursday(!selectedTh))
                selectedTh = !selectedTh },
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = buttonColorTh))
        {
            Text(
                text = "Th",
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        TextButton(
            onClick = { viewModel.onEvent(AddEditScheduleEvent.EnteredFriday(!selectedF))
                selectedF = !selectedF },
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = buttonColorF))
        {
            Text(
                text = "F",
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        TextButton(
            onClick = { viewModel.onEvent(AddEditScheduleEvent.EnteredSaturday(!selectedSa))
                selectedSa = !selectedSa },
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = buttonColorSa))
        {
            Text(
                text = "Sa",
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}