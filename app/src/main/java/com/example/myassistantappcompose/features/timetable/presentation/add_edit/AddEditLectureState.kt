package com.example.myassistantappcompose.features.timetable.presentation.add_edit

data class AddEditLectureState(
    val selectedCode: String = "Choose course code",
    val selectedTimeFrom: String = "Click to pick time",
    val selectedTimeTo: String = "Click to pick time",
    val enteredVenue: String = "",
    val selectedDay: Int = 0
)
