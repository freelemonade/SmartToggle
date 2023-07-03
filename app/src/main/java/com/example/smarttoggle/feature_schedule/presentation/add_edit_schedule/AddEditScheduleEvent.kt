package com.example.smarttoggle.feature_schedule.presentation.add_edit_schedule

import androidx.compose.ui.focus.FocusState

sealed class AddEditScheduleEvent{
    data class ChangeColor(val color: Int): AddEditScheduleEvent()
    data class EnteredScheduleName(val scheduleName: String): AddEditScheduleEvent()
    data class ChangedScheduleNameFocus(val focusState: FocusState): AddEditScheduleEvent()
    data class ChangedRingerType(val ringerType: String): AddEditScheduleEvent()
    data class EnteredStartTime(val startTime: String): AddEditScheduleEvent()
    data class EnteredEndTime(val endTime: String): AddEditScheduleEvent()
    data class EnteredSunday(val sunday: Boolean): AddEditScheduleEvent()
    data class EnteredMonday(val monday: Boolean): AddEditScheduleEvent()
    data class EnteredTuesday(val tuesday: Boolean): AddEditScheduleEvent()
    data class EnteredWednesday(val wednesday: Boolean): AddEditScheduleEvent()
    data class EnteredThursday(val thursday: Boolean): AddEditScheduleEvent()
    data class EnteredFriday(val friday: Boolean): AddEditScheduleEvent()
    data class EnteredSaturday(val saturday: Boolean): AddEditScheduleEvent()
    object SaveSchedule: AddEditScheduleEvent()
}


