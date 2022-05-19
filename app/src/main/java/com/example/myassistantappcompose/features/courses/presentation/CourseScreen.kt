package com.example.myassistantappcompose.features.courses.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myassistantappcompose.R
import com.example.myassistantappcompose.core.presentation.UiEvent
import com.example.myassistantappcompose.core.presentation.composable.StandardFab
import com.example.myassistantappcompose.core.presentation.composable.StandardTopBar
import com.example.myassistantappcompose.features.courses.data.CourseEntity
import com.example.myassistantappcompose.core.presentation.composable.StandardOutlinedTextField
import com.example.myassistantappcompose.features.courses.presentation.components.AddCourseDialog
import com.example.myassistantappcompose.features.courses.presentation.components.CourseItem
import com.example.myassistantappcompose.features.courses.presentation.components.EmptyCourses
import com.example.myassistantappcompose.features.destinations.CourseEditScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlin.math.roundToInt

@ExperimentalFoundationApi
@Destination(start = true)
@Composable
fun CourseScreen(
    viewModel: CourseViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val scaffoldState = rememberScaffoldState()
    val courseState = viewModel.courseState
    val courses by viewModel.courses.collectAsState(emptyList())
    val fabHeight = 72.dp
    val fabHeightPx = with(
        LocalDensity.current
    ) { fabHeight.roundToPx().toFloat() }
    val fabOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = fabOffsetHeightPx.value + delta
                fabOffsetHeightPx.value = newOffset.coerceIn(
                    -fabHeightPx,
                    0f
                )
                return Offset.Zero
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = it.message,
                        actionLabel = it.actionLabel
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onCourseEvent(CourseEvent.OnUndoDeleteCourse)
                    }
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.nestedScroll(nestedScrollConnection),
        topBar = { StandardTopBar(title = R.string.courses) },
        floatingActionButton = {
            StandardFab(
                modifier = Modifier.offset {
                    IntOffset(
                        x = 0,
                        y = -fabOffsetHeightPx.value.roundToInt()
                    )
                },
                contentDesc = R.string.add_new_course,
                onClick = { viewModel.onCourseEvent(CourseEvent.OnShowAddCourseDialog) }
            )
        }
    ) {
        if (courses.isEmpty()) {
            EmptyCourses()
        }

        if (courseState.showAddCourseDialog) {
            AddCourseDialog(
                viewModel = viewModel,
                title = R.string.fill_out_to_add_course,
                onConfirmedClick = {
                    viewModel.onCourseEvent(CourseEvent.OnAddCourseConfirmed)
                }
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
        ) {
            items(courses) { currentCourse ->
                CourseItem(
                    viewModel = viewModel,
                    currentCourse = currentCourse,
                    navigator = navigator
                )
            }
        }
    }
}






