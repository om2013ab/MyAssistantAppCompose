package com.example.myassistantappcompose.features.assignments.presentation

import com.example.myassistantappcompose.features.assignments.data.AssignmentEntity

data class AssignmentState(
    val selectedAssignments: List<AssignmentEntity> = emptyList(),
    val multiSelectionMode: Boolean = false,
    val showDialog: Boolean = false
)
