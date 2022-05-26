package com.example.myassistantappcompose.features.assignments.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myassistantappcompose.R
import com.example.myassistantappcompose.core.presentation.composable.StandardFab
import com.example.myassistantappcompose.core.presentation.composable.StandardTopBar
import com.example.myassistantappcompose.core.presentation.ui.theme.AssignmentColor
import com.example.myassistantappcompose.core.util.Constants.DATE_PATTERN
import com.example.myassistantappcompose.features.assignments.data.AssignmentEntity
import com.example.myassistantappcompose.features.assignments.presentation.add_edit.AddEditAssignmentViewModel
import com.example.myassistantappcompose.features.destinations.AddEditAssignmentScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Destination()
@Composable
fun AssignmentScreen(
    navigator: DestinationsNavigator,
    viewModel: AddEditAssignmentViewModel = hiltViewModel()
) {
    val assignments by viewModel.assignments.collectAsState(emptyList())

    Scaffold(
        topBar = {
            StandardTopBar(title = R.string.assignments)
        },
        floatingActionButton = {
            StandardFab(
                contentDesc = R.string.add_new_assignment,
                onClick = {navigator.navigate(AddEditAssignmentScreenDestination())}
            )
        }
    ) {
        LazyColumn(contentPadding = PaddingValues(16.dp)){
            items(items = assignments){ assignment ->
                AssignmentItem(
                    assignment = assignment,
                    onAssignmentClick = {
                        navigator.navigate(AddEditAssignmentScreenDestination(assignmentEntity = assignment))
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AssignmentItem(
    assignment: AssignmentEntity,
    onAssignmentClick : () -> Unit
) {
    val deadline = SimpleDateFormat(DATE_PATTERN, Locale.ROOT).format(assignment.deadline)
    Card(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxSize(),
        shape = RoundedCornerShape(10.dp),
        elevation = 8.dp,
        backgroundColor = AssignmentColor,
        onClick = {onAssignmentClick()}
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                text = assignment.courseCode,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 18.sp
            )
            Divider(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(id = R.string.deadline), fontSize = 12.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = deadline)
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                text = assignment.description,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

        }
    }
}