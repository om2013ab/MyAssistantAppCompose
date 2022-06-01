package com.example.myassistantappcompose.features.tests.presentation.add_edit

import java.util.*

sealed class AddEditTestEvent {
    data class OnCourseCodeChange(val code: String): AddEditTestEvent()
    data class OnDateChange(val date: Date?): AddEditTestEvent()
    data class OnTimeChange(val time: Date?): AddEditTestEvent()
    data class OnNoteChange(val note: String): AddEditTestEvent()
    object OnAddTest: AddEditTestEvent()
}
