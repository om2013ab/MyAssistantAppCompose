package com.example.myassistantappcompose.core.util

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.myassistantappcompose.R
import com.example.myassistantappcompose.features.destinations.*

sealed class BottomNavDestinations(
    val direction: DirectionDestination,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    @StringRes val label: Int,
){
    object CoursesScreen: BottomNavDestinations(
        direction = CourseScreenDestination,
        selectedIcon = Icons.Filled.Book,
        unSelectedIcon = Icons.Outlined.Book,
        label = R.string.courses,
    )
    object TimetableScreen: BottomNavDestinations(
        direction = TimetableScreenDestination,
        selectedIcon = Icons.Filled.InsertInvitation,
        unSelectedIcon = Icons.Outlined.InsertInvitation,
        label = R.string.timetable
    )
    object AssignmentsScreen: BottomNavDestinations(
        direction = AssignmentScreenDestination,
        selectedIcon = Icons.Filled.Assignment,
        unSelectedIcon = Icons.Outlined.Assignment,
        label = R.string.assignments
    )
    object TestsScreen: BottomNavDestinations(
        direction = TestScreenDestination,
        selectedIcon = Icons.Filled.NoteAdd,
        unSelectedIcon = Icons.Outlined.NoteAdd,
        label = R.string.tests
    )
    object HolidaysScreen: BottomNavDestinations(
        direction = HolidayScreenDestination,
        selectedIcon = Icons.Filled.BeachAccess,
        unSelectedIcon = Icons.Outlined.BeachAccess,
        label = R.string.holidays
    )
}
