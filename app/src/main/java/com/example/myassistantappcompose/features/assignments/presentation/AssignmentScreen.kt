package com.example.myassistantappcompose.features.assignments.presentation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.example.myassistantappcompose.R
import com.example.myassistantappcompose.core.presentation.composable.StandardFab
import com.example.myassistantappcompose.core.presentation.composable.StandardTopBar
import com.example.myassistantappcompose.features.destinations.AddEditAssignmentScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterialApi::class)
@Destination()
@Composable
fun AssignmentScreen(
    navigator: DestinationsNavigator
) {
    Scaffold(
        topBar = {
            StandardTopBar(title = R.string.assignments)
        },
        floatingActionButton = {
            StandardFab(
                contentDesc = R.string.add_new_assignment,
                onClick = {navigator.navigate(AddEditAssignmentScreenDestination())}
            )
        }
    ) {
        
    }
}