package com.example.smarttoggle.feature_schedule.presentation.schedules.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smarttoggle.feature_schedule.domain.util.OrderType
import com.example.smarttoggle.feature_schedule.domain.util.ScheduleOrder
import com.example.smarttoggle.ui.theme.WhiteGreen

@Composable
fun OrderSelectionSection(
    modifier: Modifier = Modifier,
    scheduleOrder: ScheduleOrder = ScheduleOrder.StartTime(OrderType.Descending),
    onOrderChange: (ScheduleOrder) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.onSurface
    ) {
        Column(
            modifier = modifier.background(WhiteGreen),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(
                    text = "Start time",
                    selected = scheduleOrder is ScheduleOrder.StartTime,
                    onSelect = {onOrderChange(ScheduleOrder.StartTime(scheduleOrder.orderType))}
                )
                RadioButton(
                    text = "Color",
                    selected = scheduleOrder is ScheduleOrder.Color,
                    onSelect = {onOrderChange(ScheduleOrder.Color(scheduleOrder.orderType))}
                )
                RadioButton(
                    text = "Name",
                    selected = scheduleOrder is ScheduleOrder.ScheduleName,
                    onSelect = {onOrderChange(ScheduleOrder.ScheduleName(scheduleOrder.orderType))}
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(
                    text = "Ascending",
                    selected = scheduleOrder.orderType is OrderType.Ascending,
                    onSelect = {onOrderChange(scheduleOrder.copy(OrderType.Ascending))}
                )
                Spacer(modifier = Modifier.width(10.dp))
                RadioButton(
                    text = "Descending",
                    selected = scheduleOrder.orderType is OrderType.Descending,
                    onSelect = {onOrderChange(scheduleOrder.copy(OrderType.Descending))}
                )
            }
        }
    }

}