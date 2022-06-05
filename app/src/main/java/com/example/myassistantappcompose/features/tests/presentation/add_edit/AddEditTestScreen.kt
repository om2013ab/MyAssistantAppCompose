package com.example.myassistantappcompose.features.tests.presentation.add_edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myassistantappcompose.R
import com.example.myassistantappcompose.core.presentation.composable.*
import com.example.myassistantappcompose.features.tests.data.TestEntity
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalMaterialApi
@Destination
@Composable
fun AddEditTestScreen(
    navigator: DestinationsNavigator,
    viewModel: AddEditTestViewModel = hiltViewModel(),
    testEntity: TestEntity?
) {
    val context = LocalContext.current
    val courses by viewModel.courses.collectAsState(emptyList())
    val addEditState = viewModel.addEditState
    val enableButton = addEditState.selectedCode != null && addEditState.selectedDate != null && addEditState.selectedTime != null
    Scaffold(
        topBar = {
            StandardTopBar(
                title = stringResource( if (testEntity != null) R.string.update_test else R.string.add_new_test),
                navigationIcon = Icons.Default.ArrowBack,
                onNavigationIconClick = {navigator.popBackStack()}
            )
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ) {
            CourseCodeExposedDropdownMenu(
                courseCodes = courses.map { it.courseCode },
                selectedCode = addEditState.selectedCode,
                selectedCodeChange = {
                    viewModel.onAddEditEvent(
                        AddEditTestEvent.OnCourseCodeChange(it)
                    )
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = stringResource(id = R.string.date), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            DatePicker(
                context = context,
                selectedDate = addEditState.selectedDate,
                dateChange = {
                    viewModel.onAddEditEvent(AddEditTestEvent.OnDateChange(it))
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(id = R.string.time), fontWeight = FontWeight.Bold)
            TimePicker(
                context = context,
                selectedTime = addEditState.selectedTime,
                timeChange = {
                    viewModel.onAddEditEvent(AddEditTestEvent.OnTimeChange(it))
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            StandardOutlinedTextField(
                value = addEditState.enteredNote,
                label = R.string.note,
                onValueChanged = { viewModel.onAddEditEvent(AddEditTestEvent.OnNoteChange(it)) },
            )
            Spacer(modifier = Modifier.height(60.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.onAddEditEvent(AddEditTestEvent.OnAddTest)
                    navigator.popBackStack()
                },
                enabled = enableButton
            ) {
                Text(stringResource(if (testEntity != null) R.string.save else R.string.add))
            }
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navigator.popBackStack() }
            ) {
                Text(stringResource(if (testEntity != null) R.string.back else R.string.cancel))
            }

        }
    }
}