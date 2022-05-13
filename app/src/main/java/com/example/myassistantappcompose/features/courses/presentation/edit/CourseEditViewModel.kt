package com.example.myassistantappcompose.features.courses.presentation.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
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

@HiltViewModel
class CourseEditViewModel @Inject constructor(
    db: AppDatabase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val dao = db.courseDao()

    private var courses by mutableStateOf<CourseEntity?>(null)

    var editState by mutableStateOf(EditState())
        private set

    private val uiEventChannel = Channel<UiEvent>()
    val uiEvent = uiEventChannel.receiveAsFlow()

    init {
        val courseId = savedStateHandle.get<Int>("courseId")!!
        viewModelScope.launch {
            courses = dao.getCourseById(courseId)
            courses?.let {
                editState = editState.copy(
                    courseName = it.courseName,
                    courseCode = it.courseCode,
                    courseHours = it.courseHours.toString(),
                    courseLecturer = it.courseLecturer,
                )
            }
        }
    }


    fun onEditEvent(editEvent: EditEvent) {
        when (editEvent) {
            is EditEvent.OnCourseNameChange -> {
                editState = editState.copy(courseName = editEvent.name)
            }
            is EditEvent.OnCourseCodeChange -> {
                editState = editState.copy(courseCode = editEvent.code)
            }
            is EditEvent.OnCourseHoursChange -> {
                editState = editState.copy(courseHours = editEvent.hours)
            }

            is EditEvent.OnCourseLecturerChange -> {
                editState = editState.copy(courseName = editEvent.lecturer)
            }
            is EditEvent.OnSaveChanges -> {
                viewModelScope.launch {
                    dao.insertCourse(
                        CourseEntity(
                            courseName = editState.courseName,
                            courseCode = editState.courseCode,
                            courseHours = editState.courseHours.toInt(),
                            courseLecturer = editState.courseLecturer,
                            id = courses!!.id,
                            color = courses!!.color
                        )
                    )
                    uiEventChannel.send(UiEvent.PopBackStack)
                }
            }
            is EditEvent.OnBackArrowClick -> {
                viewModelScope.launch {
                    uiEventChannel.send(UiEvent.PopBackStack)
                }
            }
        }
    }
}