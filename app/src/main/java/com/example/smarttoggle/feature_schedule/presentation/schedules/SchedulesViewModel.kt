package com.example.smarttoggle.feature_schedule.presentation.schedules

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarttoggle.feature_schedule.domain.model.Schedule
import com.example.smarttoggle.feature_schedule.domain.use_case.ScheduleUseCases
import com.example.smarttoggle.feature_schedule.domain.util.OrderType
import com.example.smarttoggle.feature_schedule.domain.util.ScheduleOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchedulesViewModel @Inject constructor(
    private val scheduleUseCases: ScheduleUseCases
): ViewModel() {

    private val _state = mutableStateOf(SchedulesState())
    val state: State<SchedulesState> = _state

    private var recentlyDeletedSchedule: Schedule? = null
    private var getSchedulesJob: Job? = null

    init {
        getSchedules(ScheduleOrder.StartTime(OrderType.Descending))
    }

    fun onEvent(event: SchedulesEvent) {
        when (event) {
            is SchedulesEvent.Order -> {
                if (state.value.scheduleOrder::class == event.scheduleOrder::class && state.value.scheduleOrder.orderType == event.scheduleOrder.orderType) {
                    return
                }
                getSchedules(event.scheduleOrder)
            }

            is SchedulesEvent.DeleteSchedule -> {
                viewModelScope.launch {
                    scheduleUseCases.deleteSchedule(event.schedule)
                    recentlyDeletedSchedule = event.schedule
                }
            }

            is SchedulesEvent.RestoreSchedule -> {
                viewModelScope.launch {
                    scheduleUseCases.addSchedule(recentlyDeletedSchedule ?: return@launch)
                    recentlyDeletedSchedule = null
                }
            }

            is SchedulesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getSchedules(scheduleOrder: ScheduleOrder) {
        getSchedulesJob?.cancel()
        getSchedulesJob = scheduleUseCases.getSchedules(scheduleOrder).onEach { schedules ->
            _state.value = state.value.copy(
                schedules = schedules,
                scheduleOrder = scheduleOrder
            )
        }.launchIn(viewModelScope)
    }
}

//    private fun timeToMin (timeString: String): Int {
//        val hr = "${timeString[0]}${timeString[1]}".replaceFirst("^0+(?!$)", "").toInt()
//        val min = "${timeString[3]}${timeString[4]}".replaceFirst("^0+(?!$)", "").toInt()
//        return hr * 3600 + min * 60
//    }