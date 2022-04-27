package com.example.myassistantappcompose.features.courses.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myassistantappcompose.R
import com.example.myassistantappcompose.core.presentation.composable.StandardTopBar
import com.example.myassistantappcompose.core.presentation.ui.theme.PrimaryColor
import com.example.myassistantappcompose.core.presentation.ui.theme.TestColor
import com.example.myassistantappcompose.features.courses.presentation.components.StandardOutlinedTextField
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.NonCancellable.cancel

@ExperimentalFoundationApi
@Destination(start = true)
@Composable
fun CourseScreen(
    viewModel: CourseViewModel = hiltViewModel()
) {
    val courseState = viewModel.courseState
    val courses by viewModel.courses.collectAsState(emptyList())

    Scaffold(
        topBar = { StandardTopBar(title = R.string.courses) },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(bottom = 60.dp),
                onClick = { viewModel.onEvent(CourseEvent.OnAddCourseClicked) }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_new_course)
                )
            }
        }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp)
        ) {

            items(courses.size) { index ->
                Card(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .padding(bottom = 24.dp)
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(courses[index].color))
                                    .padding(vertical = 8.dp),
                                text = courses[index].courseCode,
                                textAlign = TextAlign.Center,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = courses[index].courseName,
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 18.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = " Lecturer name: ${courses[index].courseLecturer}",
                                fontSize = 12.sp,
                                color = Color.Black
                            )
                            Text(
                                text = " Credit hours: ${courses[index].courseHours}",
                                fontSize = 12.sp,
                                color = Color.Black
                            )

                        }
                    }

                }
            }
        }
        if (courseState.showDialog) {
            AddCourseDialog(
                viewModel = viewModel,
                title = R.string.fill_out_to_add_course,
                onAddItemClick = {
                    viewModel.onEvent(CourseEvent.OnAddCourseConfirmed)
                }
            )
        }
    }
}

@Composable
fun AddCourseDialog(
    viewModel: CourseViewModel,
    @StringRes title: Int,
    onAddItemClick: () -> Unit
) {
    val courseState = viewModel.courseState
    AlertDialog(
        modifier = Modifier.clip(RoundedCornerShape(20.dp)),
        onDismissRequest = { viewModel.onEvent(CourseEvent.OnAddCourseDismissed) },
        text = {
            Column {
                Text(
                    text = stringResource(id = title),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                StandardOutlinedTextField(
                    value = courseState.courseName,
                    label = R.string.course_name,
                    onValueChanged = {
                        viewModel.onEvent(CourseEvent.OnCourseNameChanged(it))
                    }
                )
                StandardOutlinedTextField(
                    value = courseState.courseCode,
                    label = R.string.course_code,
                    onValueChanged = {
                        viewModel.onEvent(CourseEvent.OnCourseCodeChanged(it))
                    }
                )
                StandardOutlinedTextField(
                    value = courseState.courseHours,
                    label = R.string.credit_hours,
                    onValueChanged = {
                        viewModel.onEvent(CourseEvent.OnCourseHoursChanged(it))
                    },
                    keyboardType = KeyboardType.Number
                )
                StandardOutlinedTextField(
                    value = courseState.courseLecturer,
                    label = R.string.lecturer,
                    onValueChanged = {
                        viewModel.onEvent(CourseEvent.OnCourseLecturerChanged(it))
                    }
                )
            }
        },
        buttons = {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onAddItemClick() }
                ) {
                    Text(stringResource(id = R.string.add_label))
                }
                TextButton(
                    onClick = { viewModel.onEvent(CourseEvent.OnAddCourseDismissed) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(id = R.string.cancel))
                }
            }
        }
    )
}