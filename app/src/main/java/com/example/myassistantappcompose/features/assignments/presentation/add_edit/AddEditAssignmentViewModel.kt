package com.example.myassistantappcompose.features.assignments.presentation.add_edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassistantappcompose.features.assignments.data.AssignmentDao
import com.example.myassistantappcompose.features.assignments.data.AssignmentEntity
import com.example.myassistantappcompose.features.courses.data.CourseDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditAssignmentViewModel @Inject constructor(
    private val assignmentDao: AssignmentDao,
    private val courseDao: CourseDao,
    savedStateHandle: SavedStateHandle,
    ): ViewModel() {

    val courses = courseDao.getAllCourses()
    val assignments = assignmentDao.getAllAssignments()

    var addEditState by mutableStateOf(AddEditAssignmentState())
        private set
    private var assingment by mutableStateOf<AssignmentEntity?>(null)

    init {
        val assignmentEntity = savedStateHandle.get<AssignmentEntity>("assignmentEntity")
        if (assignmentEntity != null) {
            viewModelScope.launch {
                assingment = assignmentDao.getAssignmentById(assignmentEntity.id)
                assingment?.let {
                    addEditState = addEditState.copy(
                        selectedCode = it.courseCode,
                        selectedDeadline = it.deadline,
                        enteredDescription = it.description
                    )
                }
            }
        }
    }

    fun onAddEditEvent(event: AddEditAssignmentEvent ) {
        when(event) {
            is AddEditAssignmentEvent.OnCourseCodeChange ->{
                addEditState = addEditState.copy(
                   selectedCode = event.code
                )
            }
            is AddEditAssignmentEvent.OnDeadlineChange -> {
                addEditState = addEditState.copy(
                    selectedDeadline = event.deadline
                )
            }
            is AddEditAssignmentEvent.OnDescriptionChange ->{
                addEditState = addEditState.copy(
                    enteredDescription = event.description
                )
            }
            AddEditAssignmentEvent.OnAddAssignment -> viewModelScope.launch{
                val newAssignment = AssignmentEntity(
                    courseCode = addEditState.selectedCode!!,
                    deadline = addEditState.selectedDeadline!!,
                    description = addEditState.enteredDescription,
                    id = assingment?.id ?: 0
                )
                assignmentDao.insertAssignment(newAssignment)
            }
        }
    }

}