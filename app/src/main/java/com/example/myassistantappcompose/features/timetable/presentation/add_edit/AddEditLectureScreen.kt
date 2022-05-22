package com.example.myassistantappcompose.features.timetable.presentation.add_edit

import android.app.TimePickerDialog
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
import com.example.myassistantappcompose.core.presentation.composable.StandardTopBar
import com.example.myassistantappcompose.core.presentation.composable.StandardOutlinedTextField
import com.example.myassistantappcompose.features.timetable.data.TimetableEntity
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.util.*
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

@ExperimentalMaterialApi
@Destination
@Composable
fun AddEditLectureScreen(
    navigator: DestinationsNavigator,
    viewModel: AddEditLectureViewModel = hiltViewModel(),
    dayIndex: String?,
    timetableEntity: TimetableEntity?,
    hideBottomNav: Boolean = true
) {

    val codes = viewModel.courses.collectAsState(emptyList()).value.map {
        it.courseCode
    }
    val addEditState = viewModel.addEditState
    val context = LocalContext.current
    val enableButton = addEditState.selectedCode != null && addEditState.selectedTimeFrom != null && addEditState.selectedTimeTo != null

    Scaffold(
        topBar = {
            StandardTopBar(
                title = R.string.add_new_lecture,
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
                        AddEditScheduleEvent.OnCourseCodeChanged(
                            it
                        )
                    )
                },
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = stringResource(id = R.string.time), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column() {
                    Text(text = stringResource(R.string.time_from))
                    Spacer(modifier = Modifier.height(35.dp))
                    Text(text = stringResource(R.string.time_to))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column() {
                    TimePicker(
                        context = context,
                        selectedTime = addEditState.selectedTimeFrom,
                        timeChange = {
                            viewModel.onAddEditEvent(
                                AddEditScheduleEvent.OnTimeFromChanged(
                                    it
                                )
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    TimePicker(
                        context = context,
                        selectedTime = addEditState.selectedTimeTo,
                        timeChange = {
                            viewModel.onAddEditEvent(
                                AddEditScheduleEvent.OnTimeToChanged(
                                    it
                                )
                            )
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            StandardOutlinedTextField(
                value = addEditState.enteredVenue,
                label = R.string.venue,
                onValueChanged = { viewModel.onAddEditEvent(AddEditScheduleEvent.OnVenueChanged(it)) },
            )
            Spacer(modifier = Modifier.height(60.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.onAddEditEvent(AddEditScheduleEvent.OnAddSchedule)
                    navigator.popBackStack()
                },
                enabled = enableButton
            ) {
                Text(text = stringResource(if (timetableEntity != null) R.string.save else R.string.add))
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

@ExperimentalMaterialApi
@Composable
private fun TimePicker(
    context: Context,
    selectedTime: String?,
    timeChange: (String?) -> Unit
) {
    val calendar = Calendar.getInstance()
    val curHour = calendar.get(Calendar.HOUR)
    val curMinute = calendar.get(Calendar.MINUTE)

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            timeChange("$hour:$minute")
        }, curHour, curMinute, false
    )

    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = { timePickerDialog.show() },
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(text = selectedTime ?:"Click to pick time" , modifier = Modifier.align(Alignment.Center))
            if (selectedTime != null) {
                IconButton(
                    onClick = { timeChange(null) },
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