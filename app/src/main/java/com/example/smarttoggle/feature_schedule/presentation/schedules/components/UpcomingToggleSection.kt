package com.example.smarttoggle.feature_schedule.presentation.schedules.components

import android.app.AlarmManager
import android.content.Context
import android.media.AudioManager
import androidx.compose.animation.core.StartOffsetType.Companion.Delay
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smarttoggle.R
import com.example.smarttoggle.feature_schedule.presentation.schedules.SchedulesState
import com.example.smarttoggle.feature_schedule.presentation.schedules.SchedulesViewModel
import java.time.LocalDateTime

@Composable
fun UpcomingToggleSection(viewModel: SchedulesViewModel = hiltViewModel()) {
    Card(
        modifier = Modifier
            .height(140.dp),
        shape = RoundedCornerShape(10)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
//            for (i in viewModel.state.value.schedules.size){
//                viewModel.state.value.schedules[i].startTime
//            }




            if(viewModel.state.value.schedules.isEmpty()) {
                Text(text = "No Upcoming Toggle")
            } else {
                Text(
                    text = "",
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                when (viewModel.state.value.schedules[0].ringerType) {
                    "silent" -> Image(
                        painterResource(id = R.drawable.baseline_volume_off_24),
                        contentDescription = "silent",
                    )
                    "vibrate" -> Image(
                        painterResource(id = R.drawable.baseline_vibration_24),
                        contentDescription = "vibrate"
                    )
                    "normal" -> Image(
                        painterResource(id = R.drawable.baseline_volume_up_24),
                        contentDescription = "normal"
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = viewModel.state.value.schedules[0].startTime, fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "ArrowForward")
                    Text(
                        text = viewModel.state.value.schedules[0].endTime, fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

            }


        }
    }
}
