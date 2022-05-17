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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myassistantappcompose.R
import com.example.myassistantappcompose.core.presentation.composable.StandardFab
import com.example.myassistantappcompose.core.presentation.composable.StandardTopBar
import com.example.myassistantappcompose.features.destinations.AddLectureScreenDestination
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
    val dayIndex = viewModel.dayIndexState
    val timetables = viewModel.timetables.collectAsState(emptyList()).value.filter {
        it.dayIndex == dayIndex
    }
    val days = listOf("Mon", "Tues", "Wed", "Thur", "Fri")


    Scaffold(
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
                    navigator.navigate(AddLectureScreenDestination(dayIndex))
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

            LazyColumn(contentPadding = PaddingValues(16.dp)) {
                items(timetables) { curTimetable ->
                    TimetableItem(timetable = curTimetable)
                }
            }

        }
    }
}

@Composable
fun TimetableItem(
    timetable: TimetableEntity
) {
    Card(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxSize(),
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 8.dp),
                    text = "${timetable.timeFrom} - ${timetable.timeTo}",
                    fontSize = 16.sp
                )
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
                        text = timetable.selectedCode,
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
                        text = timetable.venue,
                        fontSize = 18.sp
                    )
                }
            }

        }
    }
}