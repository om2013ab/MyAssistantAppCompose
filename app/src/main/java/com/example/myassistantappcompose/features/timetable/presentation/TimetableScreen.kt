package com.example.myassistantappcompose.features.timetable.presentation

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
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

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
                title = R.string.timetable,
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
            TabRow(selectedTabIndex = dayIndex) {
                days.forEachIndexed { index, day ->
                    Tab(
                        selected = dayIndex == index,
                        onClick = {
                            viewModel.onDayIndexChange(index)
                        },
                        text = { Text(day) },
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

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

@ExperimentalMaterialApi
@Composable
fun TimetableItem(
    schedule: TimetableEntity,
    onOptionClick: (index: Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false)}
    Card(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxSize(),
        shape = RoundedCornerShape(5.dp),
        elevation = 8.dp
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = "${schedule.timeFrom}  -  ${schedule.timeTo}",
                    fontSize = 16.sp
                )
                IconButton(
                    onClick = { expanded = true},
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = stringResource(R.string.more_option)
                    )
                    MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(8.dp))) {
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            val options = listOf(R.string.delete_option,R.string.edit_option)
                            options.forEachIndexed { index, option ->
                                DropdownMenuItem(
                                    onClick = {
                                        expanded = false
                                        onOptionClick(index)
                                    }
                                ) { Text(stringResource(option))}
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Filled.Class,
                        contentDescription = stringResource(R.string.course_code),
                        modifier = Modifier.size(35.dp),
                        tint = Color.Blue
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = schedule.selectedCode,
                        fontSize = 18.sp
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = stringResource(R.string.venue),
                        modifier = Modifier.size(35.dp),
                        tint = Color.Blue
                    )
                    Text(
                        text = schedule.venue.ifBlank { stringResource(R.string.unknown_venue) },
                        fontSize = 18.sp
                    )
                }
            }

        }
    }
}