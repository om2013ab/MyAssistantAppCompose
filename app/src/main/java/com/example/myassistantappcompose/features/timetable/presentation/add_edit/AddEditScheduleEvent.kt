package com.example.myassistantappcompose.features.timetable.presentation.add_edit

import java.util.*

sealed class AddEditScheduleEvent {
    data class OnCourseCodeChanged(val code: String): AddEditScheduleEvent()
    data class OnTimeFromChanged(val from: Date?): AddEditScheduleEvent()
    data class OnTimeToChanged(val to: Date?): AddEditScheduleEvent()
    data class OnVenueChanged(val venue: String): AddEditScheduleEvent()
    data class OnAddSchedule(val color: Int): AddEditScheduleEvent()
}
