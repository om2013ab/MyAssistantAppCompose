package com.example.myassistantappcompose.features.assignments.presentation.add_edit

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
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
import com.example.myassistantappcompose.core.presentation.composable.CourseCodeExposedDropdownMenu
import com.example.myassistantappcompose.core.presentation.composable.DatePicker
import com.example.myassistantappcompose.core.presentation.composable.StandardOutlinedTextField
import com.example.myassistantappcompose.core.presentation.composable.StandardTopBar
import com.example.myassistantappcompose.features.assignments.data.AssignmentEntity
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Destination
@Composable
fun AddEditAssignmentScreen(
    navigator: DestinationsNavigator,
    viewModel: AddEditAssignmentViewModel = hiltViewModel(),
    hideBottomNav: Boolean = true,
    assignmentEntity: AssignmentEntity?
) {

    val courses by viewModel.courses.collectAsState(emptyList())


    val addEditState = viewModel.addEditState
    val context = LocalContext.current
    val enableButton = addEditState.selectedCode != null && addEditState.selectedDeadline != null

    Scaffold(
        topBar = {
            StandardTopBar(
                title = stringResource(if(assignmentEntity != null) R.string.edit_assignemnt else R.string.add_new_assignment),
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
                courseCodes = courses.map { it.courseCode },
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