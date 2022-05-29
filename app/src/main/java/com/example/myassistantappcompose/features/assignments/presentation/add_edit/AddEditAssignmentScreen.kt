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
import com.example.myassistantappcompose.core.util.Constants.DATE_PATTERN
import com.example.myassistantappcompose.features.assignments.data.AssignmentEntity
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalMaterialApi
@Destination
@Composable
fun AddEditAssignmentScreen(
    navigator: DestinationsNavigator,
    viewModel: AddEditAssignmentViewModel = hiltViewModel(),
    hideBottomNav: Boolean = true,
    assignmentEntity: AssignmentEntity?
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
                title = stringResource(if(assignmentEntity != null) R.string.edit_assignemnt else R.string.add_new_assignment),
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
                selectedDate = addEditState.selectedDeadline,
                dateChange = {
                    viewModel.onAddEditEvent(AddEditAssignmentEvent.OnDeadlineChange(it))
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
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
                Text(stringResource(if (assignmentEntity != null) R.string.save else R.string.add))
            }
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navigator.popBackStack() }
            ) {
                Text(stringResource(if (assignmentEntity != null) R.string.back else R.string.cancel))
            }
        }
    }
}

@Composable
private fun DatePicker(
    context: Context,
    selectedDate: Date?,
    dateChange: (Date?) -> Unit
) {
    val calender = Calendar.getInstance()
    val curYear = calender.get(Calendar.YEAR)
    val curMonth = calender.get(Calendar.MONTH)
    val curDay = calender.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
            context,
            {_, year: Int, month: Int, day: Int ->
                val date = calender.apply {
                    set(Calendar.YEAR,year)
                    set(Calendar.MONTH,month)
                    set(Calendar.DAY_OF_MONTH,day)
                }
                dateChange(date.time)

            }, curYear, curMonth, curDay
        )

    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            datePickerDialog.apply {
                datePicker.minDate = calender.timeInMillis //disable dates before today
                show()
            }},
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            val formattedDate = selectedDate?.let {
                SimpleDateFormat(DATE_PATTERN, Locale.ROOT).format(it)
            }
            Text(text = formattedDate ?: "Click to pick time", modifier = Modifier.align(Alignment.Center))
            if (selectedDate != null) {
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