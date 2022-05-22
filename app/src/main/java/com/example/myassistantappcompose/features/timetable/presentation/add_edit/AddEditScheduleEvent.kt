package com.example.myassistantappcompose.features.timetable.presentation.add_edit

sealed class AddEditScheduleEvent {
    data class OnCourseCodeChanged(val code: String): AddEditScheduleEvent()
    data class OnTimeFromChanged(val from: String?): AddEditScheduleEvent()
    data class OnTimeToChanged(val to: String?): AddEditScheduleEvent()
    data class OnVenueChanged(val venue: String): AddEditScheduleEvent()
    object OnAddSchedule: AddEditScheduleEvent()
}
