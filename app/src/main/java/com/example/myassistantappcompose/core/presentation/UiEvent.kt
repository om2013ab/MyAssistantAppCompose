package com.example.myassistantappcompose.core.presentation

import com.example.myassistantappcompose.features.destinations.DirectionDestination

sealed class UiEvent {
    data class ShowSnackBar(
        val message: String,
        val actionLabel: String? = null
    ): UiEvent()
    data class Navigate(val destination: DirectionDestination): UiEvent()
    object PopBackStack: UiEvent()
}
