package com.example.smarttoggle.feature_schedule.presentation.schedules

import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import android.provider.Settings
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.smarttoggle.AlarmItem
import com.example.smarttoggle.feature_schedule.presentation.schedules.components.OrderSelectionSection
import com.example.smarttoggle.feature_schedule.presentation.schedules.components.ScheduleItem
import com.example.smarttoggle.feature_schedule.presentation.schedules.components.UpcomingToggleSection
import com.example.smarttoggle.feature_schedule.presentation.util.Screen
import com.example.smarttoggle.ui.theme.WhiteGreen
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchedulesScreen(
    navController: NavController,
    viewModel: SchedulesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember {SnackbarHostState()}
    val scope = rememberCoroutineScope()


//    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !(getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).isNotificationPolicyAccessGranted) {
//        val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
//        startActivityForResult(this, intent, 1000)
//    }



    var alarmItemStartTime: AlarmItem? = null
    var alarmItemEndTime: AlarmItem? = null

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditScheduleScreen.route)
                },
                shape = RoundedCornerShape(100),
                containerColor = Color.Green
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add schedule")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
//        if(state.isAddingSchedule) {
//            //navController.navigate(Screen.ScheduleInputScreen.route)
//            var (alarmItemST, alarmItemET) = AddScheduleDialog(state = state, onEvent = onEvent)
//            alarmItemStartTime = alarmItemST
//            alarmItemEndTime = alarmItemET
//        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WhiteGreen)
                .padding(12.dp)
        ) {


            Text(
                text = "Upcoming Toggle",
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            UpcomingToggleSection(viewModel = viewModel)

            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = Color.LightGray, thickness = 2.dp)


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(state.schedules.size < 2) {
                    Text(
                        text = "Schedule",
                        fontSize = 25.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        text = "Schedules",
                        fontSize = 25.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        viewModel.onEvent(SchedulesEvent.ToggleOrderSection)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }


            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = slideInVertically(),
                exit = slideOutVertically()
            ) {
                OrderSelectionSection(
                    modifier = Modifier
                        .fillMaxWidth(),
                    scheduleOrder = state.scheduleOrder,
                    onOrderChange = { viewModel.onEvent(SchedulesEvent.Order(it)) }
                )
            }

            if(state.schedules.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Add your first schedule"
                    )
                }

            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(state.schedules) { schedule ->
                        ScheduleItem(
                            schedule = schedule,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(
                                        Screen.AddEditScheduleScreen.route +
                                                "?scheduleId=${schedule.id}&scheduleColor=${schedule.color}"
                                    )
                                },
                            onDeleteClick = {
                                viewModel.onEvent(SchedulesEvent.DeleteSchedule(schedule))
                                scope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        message = "Schedule deleted",
                                        actionLabel = "Undo",
                                        duration = SnackbarDuration.Short
                                    )
                                    if(result == SnackbarResult.ActionPerformed) {
                                        viewModel.onEvent(SchedulesEvent.RestoreSchedule)
                                    }
                                }
                            }
                        )



                    }
                }
            }

        }
    }
}