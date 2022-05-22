package com.example.myassistantappcompose.features.timetable.presentation.add_edit

data class AddEditLectureState(
    val selectedCode: String? = null,
    val selectedTimeFrom: String? = null,
    val selectedTimeTo: String? = null,
    val enteredVenue: String = "",
    val selectedDay: Int = 0
)
