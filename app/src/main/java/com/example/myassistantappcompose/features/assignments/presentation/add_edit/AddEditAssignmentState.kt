package com.example.myassistantappcompose.features.assignments.presentation.add_edit

data class AddEditAssignmentState(
    val selectedCode: String? = null,
    val selectedDeadline: String? = null,
    val enteredDescription: String = ""
)
