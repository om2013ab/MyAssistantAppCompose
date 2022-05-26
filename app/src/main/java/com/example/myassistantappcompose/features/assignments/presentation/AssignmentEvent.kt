package com.example.myassistantappcompose.features.assignments.presentation

import com.example.myassistantappcompose.features.assignments.data.AssignmentEntity

sealed class AssignmentEvent {
    data class OnAssignmentClick(val assignmentEntity: AssignmentEntity): AssignmentEvent()
    data class OnAssignmentLongClick(val assignmentEntity: AssignmentEntity): AssignmentEvent()
    object OnCloseMultiSelectionMode: AssignmentEvent()
    object OnShowDialog: AssignmentEvent()
    object OnDismissDialog: AssignmentEvent()
    object OnDeleteConfirmed: AssignmentEvent()
}
