package com.example.myassistantappcompose.features.timetable.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myassistantappcompose.R
import com.example.myassistantappcompose.core.presentation.UiEvent
import com.example.myassistantappcompose.core.presentation.composable.StandardFab
import com.example.myassistantappcompose.core.presentation.composable.StandardTopBar
import com.example.myassistantappcompose.features.destinations.AddEditLectureScreenDestination
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
    val state = viewModel.timetableState
    val schedules = viewModel.schedules.collectAsState(emptyList()).value.filter {
        it.dayIndex == state.dayIndex
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = scaffoldState) {
        viewModel.uiEvent.collect { event ->
            when (event) {
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
                    navigator.navigate(AddEditLectureScreenDestination(dayIndex = state.dayIndex.toString()))
                }
            )
        }
    ) {
        Column {
            TabSection(
                dayIndex = state.dayIndex,
                days = context.resources.getStringArray(R.array.days).toList(),
                onTabClick = { viewModel.onDayIndexChange(it) }
            )
            LazyColumn(
                contentPadding = PaddingValues(16.dp)
            ) {
                items(items = schedules) { schedule ->
                    TimetableItem(
                        schedule = schedule,
                        expanded = state.optionMenuExpanded && state.clickedItem == schedule,
                        onExpandedChange = {
                             viewModel.onOptionMenuClicked(it,schedule)
                        },
                        backgroundColor = schedule.color,
                        onOptionClick = { menuIndex ->
                            when (menuIndex) {
                                0 -> viewModel.onDeleteSchedule(schedule)
                                1 -> navigator.navigate(
                                    AddEditLectureScreenDestination(
                                        timetableEntity = schedule
                                    )
                                )
                            }
                        }
                    )
                }
            }

        }
    }
}