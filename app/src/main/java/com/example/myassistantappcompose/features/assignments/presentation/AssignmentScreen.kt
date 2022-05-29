package com.example.myassistantappcompose.features.assignments.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myassistantappcompose.R
import com.example.myassistantappcompose.core.presentation.UiEvent
import com.example.myassistantappcompose.core.presentation.composable.StandardAlertDialog
import com.example.myassistantappcompose.core.presentation.composable.StandardFab
import com.example.myassistantappcompose.core.presentation.composable.StandardTopBar
import com.example.myassistantappcompose.core.presentation.ui.theme.AssignmentColor
import com.example.myassistantappcompose.core.util.Constants.DATE_PATTERN
import com.example.myassistantappcompose.features.assignments.data.AssignmentEntity
import com.example.myassistantappcompose.features.destinations.AddEditAssignmentScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.webtoonscorp.android.readmore.foundation.BasicReadMoreText
import com.webtoonscorp.android.readmore.foundation.ReadMoreTextOverflow
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Destination()
@Composable
fun AssignmentScreen(
    navigator: DestinationsNavigator,
    viewModel: AssignmentViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val assignments by viewModel.assignments.collectAsState(emptyList())
    val selectedAssignments = viewModel.assignmentState.selectedAssignments
    val multiSelectionMode = viewModel.assignmentState.multiSelectionMode

    LaunchedEffect(key1 = scaffoldState) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.Navigate -> {
                    navigator.navigate(it.destination)
                }
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(it.message)
                }
                else -> Unit
            }
        }
    }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            val toolbarTitle = if (multiSelectionMode) {
                if (selectedAssignments.size == 1) "${selectedAssignments.size} assignment selected" else "${selectedAssignments.size} assignments selected"
            } else {
                stringResource(id = R.string.assignments)
            }

            val toolbarBackground =
                if (multiSelectionMode) Color.LightGray else MaterialTheme.colors.primary
            StandardTopBar(
                title = toolbarTitle,
                backgroundColor = toolbarBackground,
                navigationIcon = if (multiSelectionMode) Icons.Default.Close else null,
                onBackArrowClick = { viewModel.onAssignmentEvent(AssignmentEvent.OnCloseMultiSelectionMode) },
                actionIcon = if (multiSelectionMode) Icons.Default.Delete else null,
                onActionIconClick = { viewModel.onAssignmentEvent(AssignmentEvent.OnShowDialog) }
            )
        },
        floatingActionButton = {
            StandardFab(
                contentDesc = R.string.add_new_assignment,
                onClick = { navigator.navigate(AddEditAssignmentScreenDestination()) }
            )
        }
    ) {
        if (viewModel.assignmentState.showDialog) {
            StandardAlertDialog(
                title = R.string.confirm_deletion,
                text = R.string.delete_selected_msg,
                onConfirm = { viewModel.onAssignmentEvent(AssignmentEvent.OnDeleteConfirmed) },
                onDismiss = { viewModel.onAssignmentEvent(AssignmentEvent.OnDismissDialog) }
            )
        }
        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            items(items = assignments) { assignment ->
                AssignmentItem(
                    assignment = assignment,
                    selected = selectedAssignments.contains(assignment),
                    multiSelectionMode = multiSelectionMode,
                    onEvent = viewModel::onAssignmentEvent,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
private fun AssignmentItem(
    assignment: AssignmentEntity,
    multiSelectionMode: Boolean,
    selected: Boolean,
    onEvent: (AssignmentEvent) -> Unit
) {
    var textExpanded by rememberSaveable { mutableStateOf(false) }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.Center)
                .combinedClickable(
                    onClick = { onEvent(AssignmentEvent.OnAssignmentClick(assignment)) },
                    onLongClick = { onEvent(AssignmentEvent.OnAssignmentLongClick(assignment)) }
                ),
            shape = RoundedCornerShape(10.dp),
            elevation = 8.dp,
            backgroundColor = AssignmentColor
        ) {
            Column(

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = assignment.courseCode,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Divider()
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = stringResource(id = R.string.deadline), fontSize = 12.sp)
                Text(
                    text = SimpleDateFormat(DATE_PATTERN, Locale.ROOT).format(assignment.deadline),
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                BasicReadMoreText(
                    text = assignment.description,
                    expanded = textExpanded,
                    modifier = Modifier
                        .clickable { if(!multiSelectionMode) textExpanded = !textExpanded }
                        .padding(start = 8.dp, end = 8.dp, bottom = 16.dp),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    ),
                    readMoreText = "Read more",
                    readMoreMaxLines = 2,
                    readMoreStyle = SpanStyle(
                        color = MaterialTheme.colors.primary,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        if (multiSelectionMode) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 6.dp),
                imageVector = if (selected) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                contentDescription = null,
            )

        }
    }

}