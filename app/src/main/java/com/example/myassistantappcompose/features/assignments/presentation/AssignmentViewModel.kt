package com.example.myassistantappcompose.features.assignments.presentation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassistantappcompose.core.presentation.UiEvent
import com.example.myassistantappcompose.features.assignments.data.AssignmentDao
import com.example.myassistantappcompose.features.assignments.data.AssignmentEntity
import com.example.myassistantappcompose.features.destinations.AddEditAssignmentScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssignmentViewModel @Inject constructor(
    private val assignmentDao: AssignmentDao,
) : ViewModel() {

    val assignments = assignmentDao.getAllAssignments()

    var assignmentState by mutableStateOf(AssignmentState())
        private set


    private val uiEventChannel = Channel<UiEvent>()
    val uiEvent = uiEventChannel.receiveAsFlow()

    @OptIn(ExperimentalMaterialApi::class)
    fun onAssignmentEvent(event: AssignmentEvent) {
        when(event) {
            is AssignmentEvent.OnAssignmentClick -> {
                if (!assignmentState.multiSelectionMode) {
                    viewModelScope.launch {
                        uiEventChannel.send(
                            UiEvent.Navigate(
                                AddEditAssignmentScreenDestination(
                                    assignmentEntity = event.assignmentEntity
                                )
                            )
                        )
                    }
                } else {
                    addOrRemoveSelectedAssignments(event.assignmentEntity)
                }
            }
            is AssignmentEvent.OnAssignmentLongClick -> {
                assignmentState = assignmentState.copy(multiSelectionMode = !assignmentState.multiSelectionMode)
                addOrRemoveSelectedAssignments(event.assignmentEntity)
                if (!assignmentState.multiSelectionMode) {
                    assignmentState = assignmentState.copy(selectedAssignments = emptyList())
                }
            }

            AssignmentEvent.OnCloseMultiSelectionMode -> { assignmentState = AssignmentState()}

            AssignmentEvent.OnDeleteConfirmed -> viewModelScope.launch {
                assignmentDao.deleteSelectedAssignments(assignmentState.selectedAssignments)
                uiEventChannel.send(
                    UiEvent.ShowSnackBar(
                        message = "${assignmentState.selectedAssignments.size} assignments deleted",
                    )
                )
                assignmentState = AssignmentState()
            }
            AssignmentEvent.OnDismissDialog -> {
                assignmentState = assignmentState.copy(showDialog = false)
            }
            AssignmentEvent.OnShowDialog -> {
                assignmentState = assignmentState.copy(showDialog = true)
            }
        }
    }


    private fun addOrRemoveSelectedAssignments(assignmentEntity: AssignmentEntity) {
        if (assignmentState.selectedAssignments.contains(assignmentEntity)) {
            val selectedAssignmentsUpdated =
                assignmentState.selectedAssignments.toMutableList().apply {
                    remove(assignmentEntity)
                }
            assignmentState = assignmentState.copy(selectedAssignments = selectedAssignmentsUpdated)

            if (selectedAssignmentsUpdated.isEmpty()) {
                assignmentState = assignmentState.copy(multiSelectionMode = false)
            }
        } else {
            val selectedAssignmentsUpdated =
                assignmentState.selectedAssignments.toMutableList().apply {
                    add(assignmentEntity)
                }
            assignmentState = assignmentState.copy(selectedAssignments = selectedAssignmentsUpdated)
        }

    }
}