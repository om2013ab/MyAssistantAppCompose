package com.example.myassistantappcompose.features.assignments.presentation.add_edit

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myassistantappcompose.R
import com.example.myassistantappcompose.core.presentation.composable.CourseCodeExposedDropdownMenu
import com.example.myassistantappcompose.core.presentation.composable.StandardOutlinedTextField
import com.example.myassistantappcompose.core.presentation.composable.StandardTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.util.*

@ExperimentalMaterialApi
@Destination
@Composable
fun AddEditAssignmentScreen(
    navigator: DestinationsNavigator,
    viewModel: AddEditAssignmentViewModel = hiltViewModel()
) {

    val codes = viewModel.courses.collectAsState(emptyList()).value.map {
        it.courseCode
    }

    val addEditState = viewModel.addEditState
    val context = LocalContext.current
    val enableButton = addEditState.selectedCode != null && addEditState.selectedDeadline != null

    Scaffold(
        topBar = {
            StandardTopBar(
                title = R.string.add_new_assignment,
                navigationIcon = Icons.Default.ArrowBack,
                onBackArrowClick = { navigator.popBackStack() }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            CourseCodeExposedDropdownMenu(
                courseCodes = codes,
                selectedCode = addEditState.selectedCode,
                selectedCodeChange = {
                    viewModel.onAddEditEvent(
                        AddEditAssignmentEvent.OnCourseCodeChange(
                            it
                        )
                    )
                },
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = stringResource(id = R.string.deadline), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            DatePicker(
                context = context,
                date = addEditState.selectedDeadline,
                dateChange = {
                    viewModel.onAddEditEvent(AddEditAssignmentEvent.OnDeadlineChange(it))
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            StandardOutlinedTextField(
                value = addEditState.enteredDescription,
                label = R.string.description,
                onValueChanged = { viewModel.onAddEditEvent(AddEditAssignmentEvent.OnDescriptionChange(it)) },
            )
            Spacer(modifier = Modifier.height(60.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.onAddEditEvent(AddEditAssignmentEvent.OnAddAssignment)
                    navigator.popBackStack()
                },
                enabled = enableButton
            ) {
                Text(text = stringResource(R.string.add))
            }
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navigator.popBackStack() }
            ) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    }
}

@Composable
fun DatePicker(
    context: Context,
    date: String?,
    dateChange: (String?) -> Unit
) {
    val calender = Calendar.getInstance()
    val curYear = calender.get(Calendar.YEAR)
    val curMonth = calender.get(Calendar.MONTH)
    val curDay = calender.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog =
        DatePickerDialog(
            context,
            { _, year: Int, month: Int, day: Int ->
                dateChange("$day/${month+1}/$year")
            }, curYear, curMonth, curDay

        )
    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = { datePickerDialog.show() },
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(text = date ?: "Click to pick time", modifier = Modifier.align(Alignment.Center))
            if (date != null) {
                IconButton(
                    onClick = { dateChange(null)},
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(15.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = stringResource(id = R.string.clear_text),
                    )
                }
            }

        }

    }
}