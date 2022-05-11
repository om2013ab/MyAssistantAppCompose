package com.example.myassistantappcompose.features.courses.presentation

data class CourseState(
    val courseName: String = "",
    val courseCode: String = "",
    val courseHours: String = "",
    val courseLecturer: String = "",
    val showAddCourseDialog: Boolean = false,
    val showAlertDialog: Boolean = false
)
