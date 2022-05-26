package com.example.myassistantappcompose.features.courses.presentation

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myassistantappcompose.R
import com.example.myassistantappcompose.core.presentation.UiEvent
import com.example.myassistantappcompose.core.presentation.composable.StandardAlertDialog
import com.example.myassistantappcompose.core.presentation.composable.StandardFab
import com.example.myassistantappcompose.core.presentation.composable.StandardTopBar
import com.example.myassistantappcompose.features.courses.presentation.components.AddCourseDialog
import com.example.myassistantappcompose.features.courses.presentation.components.CourseItem
import com.example.myassistantappcompose.features.courses.presentation.components.EmptyCourses
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
    var menuExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
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

    LaunchedEffect(key1 = scaffoldState) {
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
        topBar = {
            StandardTopBar(
                title = stringResource(R.string.courses),
                actionIcon = Icons.Default.MoreVert,
                onActionIconClick = { menuExpanded = true },
                dropdownMenu = {
                    MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(8.dp))) {
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    menuExpanded = false
                                    if (courses.isEmpty()) {
                                        Toast.makeText(
                                            context,
                                            R.string.no_courses_delete,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } else {
                                        viewModel.onCourseEvent(CourseEvent.OnShowDeleteCoursesDialog)
                                    }
                                }
                            ) { Text(stringResource(R.string.delete_all)) }

                        }
                    }
                }
            )
        },
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
            EmptyCourses(imageSize = 120.dp)
        }

        if (courseState.showAddCourseDialog) {
            AddCourseDialog(
                title = R.string.fill_out_to_add_course,
                onEvent = viewModel::onCourseEvent,
                courseState = courseState
            )
        }
        if (courseState.showDeleteAllDialog) {
            StandardAlertDialog(
                title = R.string.confirm_deletion,
                text = R.string.delete_all_text,
                onConfirm = { viewModel.onCourseEvent(CourseEvent.OnDeleteCoursesConfirmed) },
                onDismiss = { viewModel.onCourseEvent(CourseEvent.OnDismissDeleteCourses) }
            )
        }
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.nestedScroll(nestedScrollConnection)
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





