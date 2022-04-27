package com.example.myassistantappcompose.features.courses.presentation

data class CourseState(
    val courseName: String = "",
    val courseCode: String = "",
    val courseHours: String = "",
    val courseLecturer: String = "",
    val showDialog: Boolean = false
)
