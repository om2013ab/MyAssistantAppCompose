package com.example.myassistantappcompose.features.timetable.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassistantappcompose.features.timetable.data.TimetableDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(
    private val dao: TimetableDao
): ViewModel() {

    var dayIndexState by mutableStateOf(0)
        private set


    val timetables = dao.getAllSchedulesByDayIndex()



    fun onDayIndexChange(dayIndex: Int) {
        dayIndexState = dayIndex
    }
}