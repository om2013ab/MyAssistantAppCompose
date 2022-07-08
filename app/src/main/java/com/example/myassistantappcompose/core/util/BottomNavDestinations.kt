package com.example.myassistantappcompose.core.util

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.myassistantappcompose.R
import com.example.myassistantappcompose.features.destinations.*
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class BottomNavDestinations(
    val direction: DirectionDestinationSpec,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    @StringRes val label: Int,
){
   @ExperimentalFoundationApi
   CoursesScreen(
        direction = CourseScreenDestination,
        selectedIcon = Icons.Filled.Book,
        unSelectedIcon = Icons.Outlined.Book,
        label = R.string.courses,
    ),
    @ExperimentalMaterialApi
    TimetableScreen(
        direction = TimetableScreenDestination,
        selectedIcon = Icons.Filled.InsertInvitation,
        unSelectedIcon = Icons.Outlined.InsertInvitation,
        label = R.string.timetable
    ),
    AssignmentsScreen(
        direction = AssignmentScreenDestination,
        selectedIcon = Icons.Filled.Assignment,
        unSelectedIcon = Icons.Outlined.Assignment,
        label = R.string.assignments
    ),

    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    TestsScreen(
        direction = TestScreenDestination,
        selectedIcon = Icons.Filled.NoteAdd,
        unSelectedIcon = Icons.Outlined.NoteAdd,
        label = R.string.tests
    ),
    @ExperimentalMaterialApi
    HolidaysScreen(
        direction = HolidayScreenDestination,
        selectedIcon = Icons.Filled.BeachAccess,
        unSelectedIcon = Icons.Outlined.BeachAccess,
        label = R.string.holidays
    )
}
