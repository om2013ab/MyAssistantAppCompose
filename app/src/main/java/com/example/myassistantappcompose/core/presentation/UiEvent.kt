package com.example.myassistantappcompose.core.presentation

import com.example.myassistantappcompose.features.destinations.DirectionDestination
import com.ramcosta.composedestinations.spec.Direction

sealed class UiEvent {
    data class ShowSnackBar(
        val message: String,
        val actionLabel: String? = null
    ): UiEvent()
    data class Navigate(val destination: Direction): UiEvent()
    object PopBackStack: UiEvent()
}
