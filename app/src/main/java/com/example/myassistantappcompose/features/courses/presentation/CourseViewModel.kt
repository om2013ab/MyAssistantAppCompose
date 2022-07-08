package com.example.myassistantappcompose.features.courses.presentation

import android.graphics.Color.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassistantappcompose.core.presentation.UiEvent
import com.example.myassistantappcompose.features.courses.data.CourseDao
import com.example.myassistantappcompose.features.courses.data.CourseEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val dao: CourseDao
):ViewModel() {

    var courseState by mutableStateOf(CourseState())
        private set

    val courses = dao.getAllCourses()

    private var deletedCourse: CourseEntity? = null


    private val uiEventChannel = Channel<UiEvent>()
    val uiEvent = uiEventChannel.receiveAsFlow()

    fun onCourseEvent(courseEvent: CourseEvent) {
        when (courseEvent) {
            is CourseEvent.OnCourseNameChange -> {
                courseState = courseState.copy(courseName = courseEvent.name)
            }

            is CourseEvent.OnCourseCodeChange -> {
                courseState = courseState.copy(courseCode = courseEvent.code)
            }

            is CourseEvent.OnCourseHoursChange -> {
                courseState = courseState.copy(courseHours = courseEvent.hours)
            }

            is CourseEvent.OnCourseLecturerChange -> {
                courseState = courseState.copy(courseLecturer = courseEvent.lecturer)
            }

            is CourseEvent.OnShowAddCourseDialog -> {
                courseState = courseState.copy(showAddCourseDialog = true)

            }
            is CourseEvent.OnAddCourseConfirmed -> {
                viewModelScope.launch {
                    val random = Random()
                    val color = argb(255,
                       random.nextInt(256),
                       random.nextInt(256),
                       random.nextInt(256)
                    )
                    val newCourse = CourseEntity(
                        courseName = courseState.courseName,
                        courseCode = courseState.courseCode,
                        courseHours = courseState.courseHours.toInt(),
                        courseLecturer = courseState.courseLecturer,
                        id = 0,
                        color = color
                    )
                    dao.insertCourse(newCourse)
                }
                courseState = CourseState(showAddCourseDialog = false)

            }
            is CourseEvent.OnDismissAddCourse -> {
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
            is CourseEvent.OnShowDeleteCoursesDialog -> {
                courseState = CourseState(showDeleteAllDialog = true)
            }
            is CourseEvent.OnDismissDeleteCourses -> {
                courseState = CourseState(showDeleteAllDialog = false)
            }
            is CourseEvent.OnDeleteCoursesConfirmed -> viewModelScope.launch{
                courseState = CourseState(showDeleteAllDialog = false)
                dao.deleteAllCourses()
            }
        }
    }
}