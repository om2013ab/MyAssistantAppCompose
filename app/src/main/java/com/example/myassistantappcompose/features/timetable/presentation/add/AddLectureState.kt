package com.example.myassistantappcompose.features.timetable.presentation.add

data class AddLectureState(
    val selectedCode: String = "Choose course code",
    val selectedTimeFrom: String = "Click to pick time",
    val selectedTimeTo: String = "Click to pick time",
    val enteredVenue: String = "Unknown venue",
    val selectedDay: Int = 0
)
