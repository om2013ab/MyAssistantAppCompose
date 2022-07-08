package com.example.myassistantappcompose.features.timetable.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myassistantappcompose.R
import com.example.myassistantappcompose.core.presentation.UiEvent
import com.example.myassistantappcompose.core.presentation.composable.StandardFab
import com.example.myassistantappcompose.core.presentation.composable.StandardTopBar
import com.example.myassistantappcompose.features.destinations.AddEditLectureScreenDestination
import com.example.myassistantappcompose.features.timetable.data.TimetableEntity
import com.example.myassistantappcompose.features.timetable.presentation.components.TabSection
import com.example.myassistantappcompose.features.timetable.presentation.components.TimetableItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Destination
@Composable
fun TimetableScreen(
    navigator: DestinationsNavigator,
    viewModel: TimetableViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val dayIndex = viewModel.dayIndexState
    val schedules = viewModel.schedules.collectAsState(emptyList()).value.filter {
        it.dayIndex == dayIndex
    }
    val days = listOf("Mon", "Tues", "Wed", "Thur", "Fri")

    LaunchedEffect(key1 = scaffoldState){
        viewModel.uiEvent.collect{ event ->
            when(event) {
                is UiEvent.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionLabel
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onUndoDeleteSchedule()
                    }
                }
                else -> Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            StandardTopBar(
                title = stringResource(R.string.timetable),
                elevation = 0.dp
            )
        },
        floatingActionButton = {
            StandardFab(
                contentDesc = R.string.add_new_lecture,
                onClick = {
                    navigator.navigate(AddEditLectureScreenDestination(dayIndex = dayIndex.toString()))
                }
            )
        }
    ) {
        Column {
            TabSection(
                dayIndex = dayIndex,
                days = days,
                viewModel = viewModel
            )
            LazyColumn(
                contentPadding = PaddingValues(16.dp)
            ) {
                items(items = schedules) { schedule ->
                    TimetableItem(
                        schedule = schedule,
                        onOptionClick = { menuIndex ->
                            when(menuIndex) {
                                0 -> viewModel.onDeleteSchedule(schedule)
                                1 -> navigator.navigate(AddEditLectureScreenDestination(timetableEntity = schedule))
                            }
                        }
                    )
                }
            }

        }
    }
}