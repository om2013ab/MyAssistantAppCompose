package com.example.myassistantappcompose.features.assignments.presentation.add_edit

import java.util.*

sealed class AddEditAssignmentEvent {
    data class OnCourseCodeChange(val code: String): AddEditAssignmentEvent()
    data class OnDeadlineChange(val deadline: Date?): AddEditAssignmentEvent()
    data class OnDescriptionChange(val description: String): AddEditAssignmentEvent()
    object OnAddAssignment:AddEditAssignmentEvent()
}
