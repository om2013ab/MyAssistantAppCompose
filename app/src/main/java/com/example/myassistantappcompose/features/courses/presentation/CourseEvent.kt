package com.example.myassistantappcompose.features.courses.presentation

import com.example.myassistantappcompose.features.courses.data.CourseEntity

sealed class CourseEvent{
    data class OnCourseNameChanged(val name: String): CourseEvent()
    data class OnCourseCodeChanged(val code: String): CourseEvent()
    data class OnCourseHoursChanged(val hours: String): CourseEvent()
    data class OnCourseLecturerChanged(val lecturer: String): CourseEvent()
    data class OnDeleteCourse(val courseEntity: CourseEntity): CourseEvent()
    object OnUndoDeleteCourse: CourseEvent()
    object OnShowAddCourseDialog: CourseEvent()
    object OnAddCourseConfirmed: CourseEvent()
    object OnDismissAddCourseDialog: CourseEvent()
}
