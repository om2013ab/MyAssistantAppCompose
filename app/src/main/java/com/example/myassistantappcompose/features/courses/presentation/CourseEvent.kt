package com.example.myassistantappcompose.features.courses.presentation

import com.example.myassistantappcompose.features.courses.data.CourseEntity

sealed class CourseEvent{
    data class OnCourseNameChange(val name: String): CourseEvent()
    data class OnCourseCodeChange(val code: String): CourseEvent()
    data class OnCourseHoursChange(val hours: String): CourseEvent()
    data class OnCourseLecturerChange(val lecturer: String): CourseEvent()
    data class OnDeleteCourse(val courseEntity: CourseEntity): CourseEvent()
    object OnUndoDeleteCourse: CourseEvent()
    object OnShowAddCourseDialog: CourseEvent()
    object OnAddCourseConfirmed: CourseEvent()
    object OnDismissAddCourse: CourseEvent()
    object OnShowDeleteCoursesDialog: CourseEvent()
    object OnDeleteCoursesConfirmed: CourseEvent()
    object OnDismissDeleteCourses: CourseEvent()

}
