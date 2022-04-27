package com.example.myassistantappcompose.features.courses.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(

):ViewModel() {

    var courseState by mutableStateOf(CourseState())

    fun onEvent(event: CourseEvent) {
        when(event) {
            is CourseEvent.OnCourseNameChanged -> {
                courseState = courseState.copy(courseName = event.name)
            }

            is CourseEvent.OnCourseCodeChanged -> {
                courseState = courseState.copy(courseName = event.code)
            }

            is CourseEvent.OnCourseHoursChanged -> {
                courseState = courseState.copy(courseName = event.hours)
            }

            is CourseEvent.OnCourseLecturerChanged -> {
                courseState = courseState.copy(courseName = event.lecturer)
            }

            CourseEvent.OnAddCourseClicked -> {
                courseState = courseState.copy(showDialog = true)

            }
            CourseEvent.OnAddCourseConfirmed -> {
                courseState = courseState.copy(showDialog = false)
            }
            CourseEvent.OnAddCourseDismissed -> {
                courseState = courseState.copy(showDialog = false)

            }
        }
    }
}