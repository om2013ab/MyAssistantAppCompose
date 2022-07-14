package com.example.myassistantappcompose.features.timetable.presentation

import com.example.myassistantappcompose.features.timetable.data.TimetableEntity

data class TimetableState(
    val dayIndex: Int = 0,
    val optionMenuExpanded: Boolean = false,
    val clickedItem: TimetableEntity? = null
)
