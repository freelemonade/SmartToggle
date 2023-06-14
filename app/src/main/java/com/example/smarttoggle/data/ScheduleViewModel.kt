package com.example.smarttoggle.data

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScheduleViewModel(private val dao: ScheduleDao): ViewModel() {


    private fun timeToMin (timeString: String): Int {
        val hr = "${timeString[0]}${timeString[1]}".replaceFirst("^0+(?!$)", "").toInt()
        val min = "${timeString[3]}${timeString[4]}".replaceFirst("^0+(?!$)", "").toInt()
        return hr * 3600 + min * 60
    }

    private val _sortType = MutableStateFlow(SortType.STARTTIME)
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _schedules = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                SortType.STARTTIME -> dao.getScheduleOrderedByStartTime()
                SortType.ENDTIME -> dao.getScheduleOrderedByEndTime()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(ScheduleState())
    val state = combine(_state, _sortType, _schedules) { state, sortType, schedules ->
        state.copy(
            schedules = schedules,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ScheduleState())

    fun onEvent(event: ScheduleEvent) {
        when(event) {
            is ScheduleEvent.DeleteSchedule -> {
                viewModelScope.launch(Dispatchers.IO) {
                    dao.deleteSchedule(event.schedule)
                }
            }
            ScheduleEvent.HideInput -> {
                _state.update { it.copy(
                    isAddingSchedule =  false
                ) }
            }
            ScheduleEvent.ShowInput -> {
                _state.update { it.copy(
                    isAddingSchedule = true
                ) }
            }
            ScheduleEvent.SaveSchedule -> {
                val scheduleName = state.value.scheduleName
                val mode = state.value.mode
                val startTime = state.value.startTime
                val endTime = state.value.endTime
                val sunday = state.value.sunday
                val monday = state.value.monday
                val tuesday = state.value.tuesday
                val wednesday = state.value.wednesday
                val thursday = state.value.thursday
                val friday = state.value.friday
                val saturday = state.value.saturday

                if(scheduleName.isBlank() || mode.isBlank() || startTime.isBlank() || endTime.isBlank() ||
                    (!sunday && !monday && !tuesday && !wednesday && !thursday && !friday && !saturday)) { return }

                if(timeToMin(startTime) >  timeToMin(endTime)) { return }

                val schedule = Schedule(
                    scheduleName = scheduleName,
                    mode = mode,
                    startTime = startTime,
                    endTime = endTime,
                    sunday = sunday,
                    monday = monday,
                    tuesday = tuesday,
                    wednesday = wednesday,
                    thursday = thursday,
                    friday = friday,
                    saturday = saturday
                )
                viewModelScope.launch(Dispatchers.IO) {
                    dao.upsertSchedule(schedule)
                }
                _state.update { it.copy(
                    isAddingSchedule = false,
                    scheduleName = "",
                    mode = "",
                    startTime = "",
                    endTime = "",
                    sunday = false,
                    monday = true,
                    tuesday = true,
                    wednesday = true,
                    thursday = true,
                    friday = true,
                    saturday = false
                ) }
            }
            is ScheduleEvent.SetEndTime -> {
                _state.update { it.copy(
                    endTime = event.endTime
                ) }
            }
            is ScheduleEvent.SetScheduleName -> {
                _state.update { it.copy(
                    scheduleName = event.scheduleName
                ) }
            }
            is ScheduleEvent.SetStartTime -> {
                _state.update { it.copy(
                    startTime = event.startTime
                ) }
            }
            is ScheduleEvent.SortSchedules -> {
                _sortType.value = event.sortType
            }
            is ScheduleEvent.SetMode -> {
                _state.update { it.copy(
                    mode = event.mode
                ) }
            }
            is ScheduleEvent.SetSunday -> {
                _state.update { it.copy(
                    sunday = event.sunday
                ) }
            }
            is ScheduleEvent.SetMonday -> {
                _state.update { it.copy(
                    monday = event.monday
                ) }
            }
            is ScheduleEvent.SetTuesday -> {
                _state.update { it.copy(
                    tuesday = event.tuesday
                ) }
            }
            is ScheduleEvent.SetWednesday -> {
                _state.update { it.copy(
                    wednesday = event.wednesday
                ) }
            }
            is ScheduleEvent.SetThursday -> {
                _state.update { it.copy(
                    thursday = event.thursday
                ) }
            }
            is ScheduleEvent.SetFriday -> {
                _state.update { it.copy(
                    friday = event.friday
                ) }
            }
            is ScheduleEvent.SetSaturday -> {
                _state.update { it.copy(
                    saturday = event.saturday
                ) }
            }
        }
    }
}