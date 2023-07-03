package com.example.smarttoggle.feature_schedule.presentation.add_edit_schedule

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarttoggle.feature_schedule.domain.model.InvalidScheduleException
import com.example.smarttoggle.feature_schedule.domain.model.Schedule
import com.example.smarttoggle.feature_schedule.domain.use_case.ScheduleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditScheduleViewModel @Inject constructor(
    private val scheduleUseCases: ScheduleUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _scheduleColor = mutableStateOf(Schedule.scheduleColors.random().toArgb())
    val scheduleColor: State<Int> = _scheduleColor

    private val _scheduleName = mutableStateOf(ScheduleTextFieldState(
        hint = "Enter schedule name..."
    ))
    val scheduleName: State<ScheduleTextFieldState> = _scheduleName

    private var _ringerType = mutableStateOf("")
    val ringerType: State<String> = _ringerType

    private val _startTime = mutableStateOf("")
    val startTime: State<String> = _startTime

    private val _endTime = mutableStateOf("")
    val endTime: State<String> = _endTime

    private val _sunday = mutableStateOf(false)
    val sunday: State<Boolean> = _sunday

    private val _monday = mutableStateOf(true)
    val monday: State<Boolean> = _monday

    private val _tuesday = mutableStateOf(true)
    val tuesday: State<Boolean> = _tuesday

    private val _wednesday = mutableStateOf(true)
    val wednesday: State<Boolean> = _wednesday

    private val _thursday = mutableStateOf(true)
    val thursday: State<Boolean> = _thursday

    private val _friday = mutableStateOf(true)
    val friday: State<Boolean> = _friday

    private val _saturday = mutableStateOf(false)
    val saturday: State<Boolean> = _saturday

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentScheduleId: Int? = null


    init {
        savedStateHandle.get<Int>("scheduleId")?.let { scheduleId ->
            if(scheduleId != -1) {
                viewModelScope.launch {
                    scheduleUseCases.getSchedule(scheduleId)?.also { schedule ->
                        currentScheduleId = schedule.id
                        _scheduleName.value = scheduleName.value.copy(
                            text = schedule.scheduleName,
                            isHintVisible = false
                        )
                        _scheduleColor.value = schedule.color
                    }
                }
            }
        }
    }


    fun onEvent(event: AddEditScheduleEvent) {
        when(event) {
            is AddEditScheduleEvent.ChangeColor -> {
                _scheduleColor.value = event.color
            }
            is AddEditScheduleEvent.EnteredScheduleName -> {
                _scheduleName.value = scheduleName.value.copy(
                    text = event.scheduleName
                )
            }
            is AddEditScheduleEvent.ChangedScheduleNameFocus -> {
                _scheduleName.value = scheduleName.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            scheduleName.value.text.isBlank()
                )
            }
            is AddEditScheduleEvent.ChangedRingerType -> {
                _ringerType.value = event.ringerType
            }
            is AddEditScheduleEvent.EnteredStartTime -> {
                _startTime.value = event.startTime
            }
            is AddEditScheduleEvent.EnteredEndTime -> {
                _endTime.value = event.endTime
            }
            is AddEditScheduleEvent.EnteredSunday -> {
                _sunday.value = event.sunday
            }
            is AddEditScheduleEvent.EnteredMonday -> {
                _monday.value = event.monday
            }
            is AddEditScheduleEvent.EnteredTuesday -> {
                _tuesday.value = event.tuesday
            }
            is AddEditScheduleEvent.EnteredWednesday -> {
                _wednesday.value = event.wednesday
            }
            is AddEditScheduleEvent.EnteredThursday -> {
                _thursday.value = event.thursday
            }
            is AddEditScheduleEvent.EnteredFriday -> {
                _friday.value = event.friday
            }
            is AddEditScheduleEvent.EnteredSaturday -> {
                _saturday.value = event.saturday
            }
            is AddEditScheduleEvent.SaveSchedule -> {
                viewModelScope.launch {
                    try {
                        scheduleUseCases.addSchedule(
                            Schedule(
                                color = scheduleColor.value,
                                scheduleName = scheduleName.value.text,
                                ringerType = ringerType.value,
                                startTime = startTime.value,
                                endTime = endTime.value,
                                sunday = sunday.value,
                                monday = monday.value,
                                tuesday = tuesday.value,
                                wednesday = wednesday.value,
                                thursday = thursday.value,
                                friday = friday.value,
                                saturday = saturday.value,
                                id = currentScheduleId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveSchedule)
                    } catch(e: InvalidScheduleException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save schedule"
                            )
                        )
                    }
                }
            }

        }
    }


    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveSchedule: UiEvent()
    }

}