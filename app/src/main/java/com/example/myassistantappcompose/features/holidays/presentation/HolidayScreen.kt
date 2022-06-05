package com.example.myassistantappcompose.features.holidays.presentation

import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import com.example.myassistantappcompose.core.presentation.composable.StandardTopBar
import com.example.myassistantappcompose.features.destinations.CountriesScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun HolidayScreen(
    navigator: DestinationsNavigator
) {

    StandardTopBar(
        title = "country",
        actionIcon = Icons.Default.ExpandMore,
        onActionIconClick = { navigator.navigate(CountriesScreenDestination()) }
    )
}


