package com.example.smarttoggle

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.smarttoggle.ui.theme.Screen
import com.example.smarttoggle.ui.theme.SmartToggleTheme
import com.example.smarttoggle.data.Schedule
import com.example.smarttoggle.data.ScheduleDatabase
import com.example.smarttoggle.data.ScheduleEvent
import com.example.smarttoggle.data.ScheduleState
import com.example.smarttoggle.data.ScheduleViewModel
import com.example.smarttoggle.data.SortType


class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ScheduleDatabase::class.java,
            "schedules.db"
        ).build()
    }
    private val viewModel by viewModels<ScheduleViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ScheduleViewModel(db.dao) as T
                }
            }
        }
    )

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartToggleTheme {
                val state by viewModel.state.collectAsState()
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    name = "SCHEDULE",
                                    route = Screen.ScheduleScreen.route,
                                    icon = Icons.Default.List,
                                    badgeCount = state.schedules.size
                                ),
                                BottomNavItem(
                                    name = "LOCATION",
                                    route = Screen.LocationScreen.route,
                                    icon = Icons.Default.LocationOn,
                                    badgeCount = 0
                                )
                            ),
                            navController = navController,
                            onItemClick = {
                                val currentRoute = navController.currentBackStackEntry?.destination?.route
                                if(currentRoute != it.route) {navController.navigate(it.route)}
                            }
                        )
                    }
                ) {
                    Box(modifier = Modifier.padding(PaddingValues(bottom = 60.dp))) {
                        Navigation(navController = navController, state = state)
                    }
                }
            }
        }
    }

    @Composable
    fun Navigation(
        navController: NavHostController,
        state: ScheduleState
    ) {
        NavHost(navController = navController, startDestination = Screen.ScheduleScreen.route) {
            composable(Screen.ScheduleScreen.route) { entry ->
                ScheduleScreen(navController = navController, state = state, onEvent = viewModel::onEvent)
            }

            composable(route = Screen.LocationScreen.route) { entry ->
                LocationScreen(navController = navController, state = state, onEvent = viewModel::onEvent)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BottomNavigationBar(
        items: List<BottomNavItem>,
        navController: NavController,
        modifier: Modifier = Modifier,
        onItemClick: (BottomNavItem) -> Unit
    ) {
        val backStackEntry = navController.currentBackStackEntryAsState()
        NavigationBar(
            modifier = modifier.height(60.dp),
            tonalElevation = 5.dp
        ) {
            items.forEach { item ->
                val selected = item.route == backStackEntry.value?.destination?.route
                NavigationBarItem(
                    selected = selected,
                    onClick = { onItemClick(item) },
                    icon = {
                        Column(horizontalAlignment = CenterHorizontally) {
                            if (item.badgeCount > 0) {
                                BadgedBox(
                                    badge = {
                                        Badge { Text(item.badgeCount.toString()) }
                                    }
                                ) {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.name
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name
                                )
                            }
                            if (selected) {
                                Text(
                                    text = item.name,
                                    textAlign = TextAlign.Center,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                )
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ScheduleScreen(
        modifier: Modifier = Modifier,
        navController: NavController,
        state: ScheduleState,
        onEvent: (ScheduleEvent) -> Unit
    ) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !(getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).isNotificationPolicyAccessGranted) {
            val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
            startActivityForResult(intent, 1000)
        }

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    onEvent(ScheduleEvent.ShowInput)
                },
                    shape = RoundedCornerShape(100)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add schedule")
                }
            }
        ) { _ ->
            if (state.isAddingSchedule) {
                //navController.navigate(Screen.ScheduleInputScreen.route)
                AddScheduleDialog(state = state, onEvent = onEvent)
            }

            Column(
                modifier = modifier
                    .fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(180.dp)
                ) {

                    Text("Upcoming Toggle", fontSize = 35.sp)
                    Spacer(modifier = modifier.height(30.dp))
                    Text("SCHEDULE NAME", fontSize = 20.sp)
                    Text("MODE", fontSize = 20.sp)
                    Text("START", fontSize = 20.sp)
                    Text("END", fontSize = 20.sp)

                }
                Spacer(modifier = modifier.height(30.dp))
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                    modifier = modifier
                        .clip(shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    item {
                        Row(modifier = modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                            verticalAlignment = CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            SortType.values().forEach { sortType ->
                                Row (
                                    modifier = modifier.clickable {
                                        onEvent(ScheduleEvent.SortSchedules(sortType))
                                    },
                                    verticalAlignment = CenterVertically
                                ) {
                                 RadioButton(selected = state.sortType == sortType,
                                     onClick = {
                                     onEvent(ScheduleEvent.SortSchedules(sortType))
                                 })
                                    Text(when(sortType.name) {
                                        "STARTTIME" -> "Start Time"
                                        "ENDTIME" -> "End Time"
                                        else -> {""}
                                    })
                                }
                            }

                        }
                    }



                    itemsIndexed(items=state.schedules,key={_,listItem->
                        listItem.hashCode()
                    }) { index, schedule ->

                        val context = LocalContext.current
                        val dismissState =  rememberDismissState(
                            confirmValueChange = {
                                when(it){
                                    DismissValue.DismissedToStart -> {

                                        onEvent(ScheduleEvent.DeleteSchedule(schedule))
                                        state.schedules.toMutableStateList().remove(schedule)
                                        println("NE")
                                        //alarmItem?.let(scheduler::cancel)
                                        true
                                    }
                                    DismissValue.Default -> {
                                        //onEvent(ScheduleEvent.SetEnable(!enable))
                                        //enable = !enable
                                        //Toast.makeText(context, "${schedule.enable}", Toast.LENGTH_SHORT).show()
                                        true
                                    }
                                    else -> false
                                }
                            }
                        )


                        SwipeToDismiss(state = dismissState, background = {
                            val color = when (dismissState.dismissDirection) {
                                DismissDirection.StartToEnd -> Color.Green
                                DismissDirection.EndToStart -> Color.Red
                                null -> Color.Black
                            }
                            Box(modifier = modifier
                                .fillMaxSize()
                                .background(color = color)
                                .padding(10.dp)) {
                                Icon(imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color.Gray,
                                    modifier = modifier.align(Alignment.CenterEnd))
                            }
                            },
                            dismissContent = {
                                ScheduleCard(modifier, schedule)
                            },
                            directions = setOf(DismissDirection.EndToStart))


                        }
                    }
                }
            }
        }


    @Composable
    fun ScheduleCard(modifier: Modifier, schedule: Schedule) {

        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.Black)
        ) {
            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally
            ) {
                Text(text = "${schedule.scheduleName}", fontSize = 30.sp)
                Spacer(modifier = modifier.height(5.dp))
                when (schedule.mode) {
                    "SILENT" -> Image(
                        painterResource(id = R.drawable.baseline_volume_off_24),
                        contentDescription = "silent"
                    )

                    "VIBRATE" -> Image(
                        painterResource(id = R.drawable.baseline_vibration_24),
                        contentDescription = "vibrate"
                    )

                    "RING" -> Image(
                        painterResource(id = R.drawable.baseline_volume_up_24),
                        contentDescription = "ring"
                    )
                }
                Spacer(modifier = modifier.height(5.dp))
                Row(
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "${schedule.startTime}", fontSize = 20.sp)
                    Spacer(modifier = modifier.width(15.dp))
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Arrow")
                    Spacer(modifier = modifier.width(15.dp))
                    Text(text = "${schedule.endTime}", fontSize = 20.sp)
                }
                Spacer(modifier = modifier.height(20.dp))
                Row(
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    DayOfWeek(modifier = modifier, day = "Su", schedule.sunday)
                    Spacer(modifier = Modifier.width(15.dp))
                    DayOfWeek(modifier = modifier, day = "M", schedule.monday)
                    Spacer(modifier = Modifier.width(15.dp))
                    DayOfWeek(modifier = modifier, day = "Tu", schedule.tuesday)
                    Spacer(modifier = Modifier.width(15.dp))
                    DayOfWeek(modifier = modifier, day = "W", schedule.wednesday)
                    Spacer(modifier = Modifier.width(15.dp))
                    DayOfWeek(modifier = modifier, day = "Th", schedule.thursday)
                    Spacer(modifier = Modifier.width(15.dp))
                    DayOfWeek(modifier = modifier, day = "F", schedule.friday)
                    Spacer(modifier = Modifier.width(15.dp))
                    DayOfWeek(modifier = modifier, day = "Sa", schedule.saturday)

                }
            }
        }
    }


    @Composable
    fun DayOfWeek(modifier: Modifier, day: String, selected: Boolean) {
        Text(
            modifier = modifier
                .drawBehind {
                    drawCircle(
                        color = if (selected) Color.Green else Color(R.color.androidgreen),
                        radius = 35f
                    )
                },
            text = day,
            textAlign = TextAlign.Center
        )
    }





    @Composable
    fun LocationScreen(
        navController: NavController,
        state: ScheduleState,
        onEvent: (ScheduleEvent) -> Unit
    ) {

    }
}


