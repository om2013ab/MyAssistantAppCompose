package com.example.myassistantappcompose.features.timetable.presentation.add_edit

import android.annotation.SuppressLint
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
import com.example.myassistantappcompose.core.presentation.composable.TimePicker
import com.example.myassistantappcompose.core.util.Constants.TIME_PATTERN
import com.example.myassistantappcompose.features.timetable.data.TimetableEntity
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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

    val courses by viewModel.courses.collectAsState(emptyList())

    val codes = courses.map {
        it.courseCode
    }
    val colors = courses.map {
        it.color
    }
    val addEditState = viewModel.addEditState
    val context = LocalContext.current
    val enableButton = addEditState.selectedCode != null && addEditState.selectedTimeFrom != null && addEditState.selectedTimeTo != null

    Scaffold(
        topBar = {
            StandardTopBar(
                title = stringResource(if (timetableEntity != null) R.string.edit_lecture else R.string.add_new_lecture),
                navigationIcon = Icons.Default.ArrowBack,
                onNavigationIconClick = { navigator.popBackStack() }
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
                            code = it,
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
                    val colorOfSelectedCode = colors[codes.indexOf(addEditState.selectedCode)]
                    viewModel.onAddEditEvent(AddEditScheduleEvent.OnAddSchedule(colorOfSelectedCode))
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

