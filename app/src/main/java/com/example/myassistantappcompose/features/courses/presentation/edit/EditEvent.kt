package com.example.myassistantappcompose.features.courses.presentation.edit

sealed class EditEvent {
    data class OnCourseNameChange(val name: String): EditEvent()
    data class OnCourseCodeChange(val code: String): EditEvent()
    data class OnCourseHoursChange(val hours: String): EditEvent()
    data class OnCourseLecturerChange(val lecturer: String): EditEvent()
    object OnSaveChanges: EditEvent()
    object OnBackArrowClick: EditEvent()
}
