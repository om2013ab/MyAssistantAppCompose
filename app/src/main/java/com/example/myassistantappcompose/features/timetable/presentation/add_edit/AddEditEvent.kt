package com.example.myassistantappcompose.features.timetable.presentation.add_edit

sealed class AddEditEvent {
    data class OnCourseCodeChanged(val code: String): AddEditEvent()
    data class OnTimeFromChanged(val from: String): AddEditEvent()
    data class OnTimeToChanged(val to: String): AddEditEvent()
    data class OnVenueChanged(val venue: String): AddEditEvent()
    object OnAddSchedule: AddEditEvent()
}
