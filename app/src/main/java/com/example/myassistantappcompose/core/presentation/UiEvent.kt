package com.example.myassistantappcompose.core.presentation

sealed class UiEvent {
    data class ShowSnackBar(
        val message: String,
        val actionLabel: String? = null
    ): UiEvent()
}
