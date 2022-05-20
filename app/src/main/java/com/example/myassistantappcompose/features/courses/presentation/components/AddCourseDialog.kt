package com.example.myassistantappcompose.features.courses.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import com.example.myassistantappcompose.features.courses.presentation.CourseState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddCourseDialog(
    @StringRes title: Int,
    courseState: CourseState,
    onEvent: (CourseEvent) -> Unit,
) {
    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.fillMaxSize(),
        onDismissRequest = { onEvent(CourseEvent.OnDismissAddCourse) },
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
                        onEvent(CourseEvent.OnCourseNameChange(it))
                    }
                )
                StandardOutlinedTextField(
                    value = courseState.courseCode,
                    label = R.string.course_code,
                    onValueChanged = {
                        onEvent(CourseEvent.OnCourseCodeChange(it))
                    }
                )
                StandardOutlinedTextField(
                    value = courseState.courseHours,
                    label = R.string.credit_hours,
                    onValueChanged = {
                        onEvent(CourseEvent.OnCourseHoursChange(it))
                    },
                    keyboardType = KeyboardType.Number
                )
                StandardOutlinedTextField(
                    value = courseState.courseLecturer,
                    label = R.string.lecturer,
                    onValueChanged = {
                        onEvent(CourseEvent.OnCourseLecturerChange(it))
                    },
                    spacer = 30.dp
                )
            }
        },
        buttons = {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onEvent(CourseEvent.OnAddCourseConfirmed) }
                ) {
                    Text(stringResource(id = R.string.add_label))
                }
                TextButton(
                    onClick = { onEvent(CourseEvent.OnDismissAddCourse) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
        }
    )
}