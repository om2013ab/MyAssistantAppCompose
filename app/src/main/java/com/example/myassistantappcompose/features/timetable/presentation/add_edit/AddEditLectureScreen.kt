package com.example.myassistantappcompose.features.timetable.presentation.add_edit

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myassistantappcompose.R
import com.example.myassistantappcompose.core.presentation.composable.StandardTopBar
import com.example.myassistantappcompose.features.courses.presentation.components.StandardOutlinedTextField
import com.example.myassistantappcompose.features.timetable.data.TimetableEntity
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

@ExperimentalMaterialApi
@Destination
@Composable
fun AddEditLectureScreen(
    navigator: DestinationsNavigator,
    viewModelEdit: AddEditLectureViewModel = hiltViewModel(),
    dayIndex: String?,
    timetableEntity: TimetableEntity?,
    hideBottomNav: Boolean = true
) {

    val codes = viewModelEdit.courses.collectAsState(emptyList()).value.map {
        it.courseCode
    }
    val addLectureState = viewModelEdit.addEditLectureState
    val context = LocalContext.current


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
                selectedCode = addLectureState.selectedCode,
                selectedCodeChange = { viewModelEdit.onAddEditEvent(AddEditEvent.OnCourseCodeChanged(it)) },
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
                        selectedTime = addLectureState.selectedTimeFrom,
                        initHour = System.currentTimeMillis().hours.toInt(DurationUnit.HOURS),
                        initMinute = System.currentTimeMillis().minutes.toInt(DurationUnit.MINUTES),
                        timeChange = { viewModelEdit.onAddEditEvent(AddEditEvent.OnTimeFromChanged(it)) }
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    TimePicker(
                        context = context,
                        selectedTime = addLectureState.selectedTimeTo,
                        initHour = System.currentTimeMillis().hours.toInt(DurationUnit.HOURS),
                        initMinute = System.currentTimeMillis().minutes.toInt(DurationUnit.MINUTES),
                        timeChange = { viewModelEdit.onAddEditEvent(AddEditEvent.OnTimeToChanged(it)) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            StandardOutlinedTextField(
                value = addLectureState.enteredVenue,
                label = R.string.venue,
                onValueChanged = { viewModelEdit.onAddEditEvent(AddEditEvent.OnVenueChanged(it)) },
                spacer = 60.dp
            )
            val enableButton =
                codes.contains(addLectureState.selectedCode) && addLectureState.selectedTimeFrom.any { it.isDigit() } && addLectureState.selectedTimeTo.any { it.isDigit() }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModelEdit.onAddEditEvent(AddEditEvent.OnAddSchedule)
                    navigator.popBackStack()
                },
                enabled = enableButton
            ) {
                Text(text = stringResource(if(timetableEntity != null) R.string.save else R.string.add))
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
private fun CourseCodeExposedDropdownMenu(
    courseCodes: List<String>,
    selectedCode: String,
    selectedCodeChange: (String) -> Unit,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            value = selectedCode,
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            label = { Text(text = "Course Code", fontWeight = FontWeight.Bold) }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            if (courseCodes.isNullOrEmpty()){
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text(text = stringResource(R.string.empty_codes))
                }
            }
            courseCodes.forEach {
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        selectedCodeChange(it)
                    }
                ) {
                    Text(text = it)
                }
            }
        }

    }
}


@ExperimentalMaterialApi
@Composable
private fun TimePicker(
    context: Context,
    initHour: Int,
    initMinute: Int,
    selectedTime: String,
    timeChange: (String) -> Unit
) {

    val timePickerDialog by remember {
        mutableStateOf(
            TimePickerDialog(
                context,
                { _, hour: Int, minute: Int ->
                    timeChange("$hour:$minute")
                }, initHour, initMinute, false
            )
        )
    }
    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = { timePickerDialog.show() },
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(text = selectedTime, modifier = Modifier.align(Alignment.Center))
            if (selectedTime.any { it.isDigit() }) {
                IconButton(
                    onClick = { timeChange("Click to pick time") },
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