package com.example.myassistantappcompose.features.courses.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassistantappcompose.core.data.AppDatabase
import com.example.myassistantappcompose.core.presentation.UiEvent
import com.example.myassistantappcompose.features.courses.data.CourseEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val db: AppDatabase
):ViewModel() {

    private val dao = db.courseDao()
    var courseState by mutableStateOf(CourseState())
    val courses = dao.getAllCourses()

    private var deletedCourse: CourseEntity? = null

    private val colors = listOf(
        Color.Blue,
        Color.Green,
        Color.Red,
        Color.Magenta,
        Color.Cyan,
    ).map { it.toArgb() }

    private val uiEventChannel = Channel<UiEvent>()
    val uiEvent = uiEventChannel.receiveAsFlow()

    fun onCourseEvent(courseEvent: CourseEvent) {
        when (courseEvent) {
            is CourseEvent.OnCourseNameChanged -> {
                courseState = courseState.copy(courseName = courseEvent.name)
            }

            is CourseEvent.OnCourseCodeChanged -> {
                courseState = courseState.copy(courseCode = courseEvent.code)
            }

            is CourseEvent.OnCourseHoursChanged -> {
                courseState = courseState.copy(courseHours = courseEvent.hours)
            }

            is CourseEvent.OnCourseLecturerChanged -> {
                courseState = courseState.copy(courseLecturer = courseEvent.lecturer)
            }

            is CourseEvent.OnShowAddCourseDialog -> {
                courseState = courseState.copy(showAddCourseDialog = true)

            }
            is CourseEvent.OnAddCourseConfirmed -> {
                viewModelScope.launch {
                    val newCourse = CourseEntity(
                        courseName = courseState.courseName,
                        courseCode = courseState.courseCode,
                        courseHours = courseState.courseHours.toInt(),
                        courseLecturer = courseState.courseLecturer,
                        id = 0,
                        color = Random.nextInt(from = colors.first(), until = colors.last())
                    )
                    dao.insertCourse(newCourse)
                }
                courseState = CourseState(showAddCourseDialog = false)

            }
            is CourseEvent.OnDismissAddCourseDialog -> {
                courseState = CourseState(showAddCourseDialog = false)
            }
            is CourseEvent.OnDeleteCourse -> {
                deletedCourse = courseEvent.courseEntity
                viewModelScope.launch {
                    dao.deleteCourse(courseEvent.courseEntity)
                    uiEventChannel.send(UiEvent.ShowSnackBar(
                        message = "Course deleted",
                        actionLabel = "UNDO"
                    ))

                }
            }
            is CourseEvent.OnUndoDeleteCourse -> {
                viewModelScope.launch {
                    deletedCourse?.let {
                        dao.insertCourse(it)
                    }
                }
            }
        }
    }
}