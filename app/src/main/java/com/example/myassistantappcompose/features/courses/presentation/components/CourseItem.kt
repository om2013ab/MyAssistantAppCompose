package com.example.myassistantappcompose.features.courses.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myassistantappcompose.R
import com.example.myassistantappcompose.features.courses.data.CourseEntity
import com.example.myassistantappcompose.features.courses.presentation.CourseEvent
import com.example.myassistantappcompose.features.courses.presentation.CourseViewModel
import com.example.myassistantappcompose.features.destinations.CourseEditScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun CourseItem(
    viewModel: CourseViewModel,
    currentCourse: CourseEntity,
    navigator: DestinationsNavigator
) {
    Card(
        modifier = Modifier.padding(bottom = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .background(Color(currentCourse.color))
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = currentCourse.courseCode,
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Row {
                    IconButton(
                        onClick = { navigator.navigate(CourseEditScreenDestination(currentCourse.id)) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(id = R.string.edit_option),
                            tint = Color.White
                        )
                    }
                    IconButton(
                        onClick = { viewModel.onCourseEvent(CourseEvent.OnDeleteCourse(currentCourse)) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(id = R.string.delete_option),
                            tint = Color.White
                        )
                    }
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = currentCourse.courseName,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Lecturer name: ${currentCourse.courseLecturer}",
                    fontSize = 12.sp,
                    color = Color.Black
                )
                Text(
                    text = "Credit hours: ${currentCourse.courseHours}",
                    fontSize = 12.sp,
                    color = Color.Black
                )

            }
        }
    }
}