package com.example.myassistantappcompose.features.courses.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.myassistantappcompose.R
import com.example.myassistantappcompose.core.presentation.composable.StandardOutlinedTextField
import com.example.myassistantappcompose.features.courses.presentation.CourseEvent
import com.example.myassistantappcompose.features.courses.presentation.CourseViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddCourseDialog(
    viewModel: CourseViewModel,
    @StringRes title: Int,
    onConfirmedClick: () -> Unit,
) {
    val courseState = viewModel.courseState
    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.fillMaxSize(),
        onDismissRequest = { viewModel.onCourseEvent(CourseEvent.OnDismissAddCourseDialog) },
        text = {
            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = title),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                StandardOutlinedTextField(
                    value = courseState.courseName,
                    label = R.string.course_name,
                    onValueChanged = {
                        viewModel.onCourseEvent(CourseEvent.OnCourseNameChange(it))
                    }
                )
                StandardOutlinedTextField(
                    value = courseState.courseCode,
                    label = R.string.course_code,
                    onValueChanged = {
                        viewModel.onCourseEvent(CourseEvent.OnCourseCodeChange(it))
                    }
                )
                StandardOutlinedTextField(
                    value = courseState.courseHours,
                    label = R.string.credit_hours,
                    onValueChanged = {
                        viewModel.onCourseEvent(CourseEvent.OnCourseHoursChange(it))
                    },
                    keyboardType = KeyboardType.Number
                )
                StandardOutlinedTextField(
                    value = courseState.courseLecturer,
                    label = R.string.lecturer,
                    onValueChanged = {
                        viewModel.onCourseEvent(CourseEvent.OnCourseLecturerChange(it))
                    },
                    spacer = 50.dp
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
                    onClick = { onConfirmedClick() }
                ) {
                    Text(stringResource(id = R.string.add_label))
                }
                TextButton(
                    onClick = { viewModel.onCourseEvent(CourseEvent.OnDismissAddCourseDialog) },
                    modifier = Modifier.fillMaxWidth()
                ) { Text(stringResource(R.string.cancel)) }
            }
        }
    )
}