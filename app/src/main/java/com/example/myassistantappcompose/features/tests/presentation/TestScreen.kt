package com.example.myassistantappcompose.features.tests.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myassistantappcompose.R
import com.example.myassistantappcompose.core.presentation.UiEvent
import com.example.myassistantappcompose.core.presentation.composable.ExpandingText
import com.example.myassistantappcompose.core.presentation.composable.StandardAlertDialog
import com.example.myassistantappcompose.core.presentation.composable.StandardFab
import com.example.myassistantappcompose.core.presentation.composable.StandardTopBar
import com.example.myassistantappcompose.core.presentation.ui.theme.TestColor
import com.example.myassistantappcompose.core.util.Constants.DATE_PATTERN
import com.example.myassistantappcompose.core.util.Constants.TIME_PATTERN
import com.example.myassistantappcompose.features.destinations.AddEditTestScreenDestination
import com.example.myassistantappcompose.features.tests.data.TestEntity
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Destination
@Composable
fun TestScreen(
    navigator: DestinationsNavigator,
    viewModel: TestViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val tests by viewModel.tests.collectAsState(emptyList())
    val selectedTests = viewModel.testState.selectedTests
    val multiSelectionMode = viewModel.testState.multiSelectionMode

    LaunchedEffect(key1 = scaffoldState) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navigator.navigate(event.destination)
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            val toolbarTitle = if (multiSelectionMode) {
                if (selectedTests.size == 1) "1 assignment selected" else "${selectedTests.size} assignments selected"
            } else {
                stringResource(id = R.string.tests)
            }
            val toolbarBackground =
                if (multiSelectionMode) Color.LightGray else MaterialTheme.colors.primary

            StandardTopBar(
                title = toolbarTitle,
                backgroundColor = toolbarBackground,
                navigationIcon = if (multiSelectionMode) Icons.Default.Close else null,
                onNavigationIconClick = {viewModel.onTestEvent(TestEvent.OnCloseMultiSelectionMode)},
                actionIcon = if (multiSelectionMode) Icons.Default.Delete else null,
                onActionIconClick = {viewModel.onTestEvent(TestEvent.OnShowDialog)}
            )
        },
        floatingActionButton = {
            StandardFab(
                contentDesc = R.string.add_new_test,
                onClick = { navigator.navigate(AddEditTestScreenDestination()) }
            )
        }
    ) {
        if (viewModel.testState.showDialog) {
            StandardAlertDialog(
                title = R.string.confirm_deletion,
                text = R.string.delete_test_msg,
                onConfirm = {viewModel.onTestEvent(TestEvent.OnDeleteConfirmed)},
                onDismiss = {viewModel.onTestEvent(TestEvent.OnDismissDialog)}
            )
        }
        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            items(items = tests) { test ->
                TestItem(
                    test = test,
                    selected = selectedTests.contains(test),
                    multiSelectionMode = multiSelectionMode,
                    onEvent = viewModel::onTestEvent
                )
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
private fun TestItem(
    test: TestEntity,
    multiSelectionMode: Boolean,
    selected: Boolean,
    onEvent: (TestEvent) -> Unit
) {
    var textExpanded by rememberSaveable { mutableStateOf(false) }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize() ){
        Card(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .combinedClickable(
                    onClick = { onEvent(TestEvent.OnTestClick(test)) },
                    onLongClick = { onEvent(TestEvent.OnTestLongClick(test)) }
                ),
            shape = RoundedCornerShape(10.dp),
            elevation = 8.dp,
            backgroundColor = TestColor
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = test.courseCode,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Divider()
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = stringResource(R.string.date), fontSize = 12.sp)
                        Text(
                            text = SimpleDateFormat(DATE_PATTERN, Locale.ROOT).format(test.date),
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = stringResource(R.string.time), fontSize = 12.sp)
                        Text(
                            text = SimpleDateFormat(TIME_PATTERN, Locale.ROOT).format(test.time),
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                ExpandingText(text = test.note, multiSelectionMode = multiSelectionMode)
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