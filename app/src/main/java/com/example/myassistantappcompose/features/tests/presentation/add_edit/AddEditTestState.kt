package com.example.myassistantappcompose.features.tests.presentation.add_edit

import java.util.*

data class AddEditTestState(
    val selectedCode: String? = null,
    val selectedDate: Date? = null,
    val selectedTime: Date? = null,
    val enteredNote: String = ""
)
