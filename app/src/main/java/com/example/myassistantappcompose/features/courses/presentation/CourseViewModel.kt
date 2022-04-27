package com.example.myassistantappcompose.features.courses.presentation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassistantappcompose.core.data.AppDatabase
import com.example.myassistantappcompose.features.courses.data.CourseEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val db: AppDatabase
):ViewModel() {

    var courseState by mutableStateOf(CourseState())
    val courses = db.courseDao().getAllCourses()

    private val colors = listOf(
        Color.Blue,
        Color.Green,
        Color.Red,
        Color.Magenta,
        Color.Cyan,
    )

    fun onEvent(event: CourseEvent) {
        when(event) {
            is CourseEvent.OnCourseNameChanged -> {
                courseState = courseState.copy(courseName = event.name)
            }

            is CourseEvent.OnCourseCodeChanged -> {
                courseState = courseState.copy(courseCode = event.code)
            }

            is CourseEvent.OnCourseHoursChanged -> {
                courseState = courseState.copy(courseHours = event.hours)
            }

            is CourseEvent.OnCourseLecturerChanged -> {
                courseState = courseState.copy(courseLecturer = event.lecturer)
            }

            CourseEvent.OnAddCourseClicked -> {
                courseState = courseState.copy(showDialog = true)

            }
            CourseEvent.OnAddCourseConfirmed -> {
                courseState = courseState.copy(showDialog = false)
                viewModelScope.launch {
                    val newCourse = CourseEntity(
                        courseName = courseState.courseName,
                        courseCode = courseState.courseCode,
                        courseHours = courseState.courseHours.toInt(),
                        courseLecturer = courseState.courseLecturer,
                        id = 0,
                        color = colors.random().toArgb()
                    )
                    db.courseDao().insertCourse(newCourse)
                }
            }
            CourseEvent.OnAddCourseDismissed -> {
                courseState = courseState.copy(showDialog = false)

            }
        }
    }
}