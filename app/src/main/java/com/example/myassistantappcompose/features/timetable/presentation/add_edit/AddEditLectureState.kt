package com.example.myassistantappcompose.features.timetable.presentation.add_edit

import java.util.*

data class AddEditLectureState(
    val selectedCode: String? = null,
    val selectedTimeFrom: Date? = null,
    val selectedTimeTo: Date? = null,
    val enteredVenue: String = "",
    val selectedDay: Int = 0
)
