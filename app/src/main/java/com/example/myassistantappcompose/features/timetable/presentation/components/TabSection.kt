package com.example.myassistantappcompose.features.timetable.presentation.components

import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.myassistantappcompose.features.timetable.presentation.TimetableViewModel

@Composable
fun TabSection(
    dayIndex: Int,
    days: List<String>,
    viewModel: TimetableViewModel
) {
    TabRow(selectedTabIndex = dayIndex) {
        days.forEachIndexed { index, day ->
            Tab(
                selected = dayIndex == index,
                onClick = {
                    viewModel.onDayIndexChange(index)
                },
                text = { Text(day) },
            )
        }
    }
}