package com.example.myassistantappcompose.features.courses.presentation.edit

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myassistantappcompose.R
import com.example.myassistantappcompose.core.presentation.UiEvent
import com.example.myassistantappcompose.core.presentation.composable.StandardTopBar
import com.example.myassistantappcompose.features.courses.presentation.components.StandardOutlinedTextField
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun CourseEditScreen(
    navigator: DestinationsNavigator,
    courseId: Int,
    viewModel: CourseEditViewModel = hiltViewModel(),
    hideBottomNav: Boolean = true
) {

    val editState = viewModel.editState

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect{event ->
            when (event) {
                UiEvent.PopBackStack -> {navigator.popBackStack()}
                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            StandardTopBar(
                title = R.string.edit_course,
                navigationIcon = Icons.Default.ArrowBack,
                onBackArrowClick = {
                    viewModel.onEditEvent(EditEvent.OnBackArrowClick)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {viewModel.onEditEvent(EditEvent.OnSaveChanges)}) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = stringResource(id = R.string.save_changes),
                    tint = Color.White
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                StandardOutlinedTextField(
                    value = editState.courseName,
                    label = R.string.course_name,
                    onValueChanged = {viewModel.onEditEvent(EditEvent.OnCourseNameChange(it))}
                )
                StandardOutlinedTextField(
                    value = editState.courseCode,
                    label = R.string.course_code,
                    onValueChanged = {viewModel.onEditEvent(EditEvent.OnCourseCodeChange(it))}
                )
                StandardOutlinedTextField(
                    value = editState.courseHours,
                    label = R.string.credit_hours,
                    onValueChanged = {viewModel.onEditEvent(EditEvent.OnCourseHoursChange(it))},
                    keyboardType = KeyboardType.Number,
                )
                StandardOutlinedTextField(
                    value = editState.courseLecturer,
                    label = R.string.lecturer,
                    onValueChanged = {viewModel.onEditEvent(EditEvent.OnCourseLecturerChange(it))}
                )
            }
        }
    }
}