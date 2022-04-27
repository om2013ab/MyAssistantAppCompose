package com.example.myassistantappcompose.features.courses.presentation

sealed class CourseEvent{
    data class OnCourseNameChanged(val name: String): CourseEvent()
    data class OnCourseCodeChanged(val code: String): CourseEvent()
    data class OnCourseHoursChanged(val hours: String): CourseEvent()
    data class OnCourseLecturerChanged(val lecturer: String): CourseEvent()
    object OnAddCourseClicked: CourseEvent()
    object OnAddCourseConfirmed: CourseEvent()
    object OnAddCourseDismissed: CourseEvent()
}
