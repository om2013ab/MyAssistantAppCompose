package com.example.myassistantappcompose.features.assignments.presentation.add_edit

sealed class AddEditAssignmentEvent {
    data class OnCourseCodeChange(val code: String): AddEditAssignmentEvent()
    data class OnDeadlineChange(val deadline: String?): AddEditAssignmentEvent()
    data class OnDescriptionChange(val description: String): AddEditAssignmentEvent()
    object OnAddAssignment:AddEditAssignmentEvent()
}
