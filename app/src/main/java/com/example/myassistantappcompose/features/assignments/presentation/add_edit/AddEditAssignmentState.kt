package com.example.myassistantappcompose.features.assignments.presentation.add_edit

import java.util.*

data class AddEditAssignmentState(
    val selectedCode: String? = null,
    val selectedDeadline: Date? = null,
    val enteredDescription: String = ""
)
