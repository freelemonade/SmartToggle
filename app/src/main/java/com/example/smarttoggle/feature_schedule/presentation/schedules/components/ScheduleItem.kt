package com.example.smarttoggle.feature_schedule.presentation.schedules.components

import android.text.method.ArrowKeyMovementMethod
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarttoggle.R
import com.example.smarttoggle.feature_schedule.domain.model.Schedule
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun ScheduleItem(schedule: Schedule, modifier: Modifier, onDeleteClick: () -> Unit) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10)
    ) {
        Column(
            modifier = Modifier
                .background(Color(schedule.color))
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = schedule.scheduleName,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(5.dp))
            when (schedule.ringerType) {
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
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = schedule.startTime,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(15.dp))
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "ArrowForward")
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = schedule.endTime,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                DayOfWeek(day = "Su", selected = schedule.sunday)
                DayOfWeek(day = "M", selected = schedule.monday)
                DayOfWeek(day = "Tu", selected = schedule.tuesday)
                DayOfWeek(day = "W", selected = schedule.wednesday)
                DayOfWeek(day = "Th", selected = schedule.thursday)
                DayOfWeek(day = "F", selected = schedule.friday)
                DayOfWeek(day = "Sa", selected = schedule.saturday)
            }
        }

        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete note",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun DayOfWeek(day: String, selected: Boolean) {
    Text(
        modifier = Modifier
            .drawBehind {
                drawCircle(
                    color = if (selected) Color.Green else Color(R.color.androidgreen),
                    radius = 50f
                )
            },
        text = day,
        textAlign = TextAlign.Center
    )
}