package com.example.myassistantappcompose.features.timetable.presentation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassistantappcompose.core.presentation.UiEvent
import com.example.myassistantappcompose.features.timetable.data.TimetableDao
import com.example.myassistantappcompose.features.timetable.data.TimetableEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(
    private val dao: TimetableDao
): ViewModel() {

    var dayIndexState by mutableStateOf(0)
        private set


    val schedules = dao.getAllSchedules()

    private var deletedSchedule: TimetableEntity? = null

    private val uiEventChannel = Channel<UiEvent>()
    val uiEvent = uiEventChannel.receiveAsFlow()

    fun onDayIndexChange(dayIndex: Int) {
        dayIndexState = dayIndex
    }

    fun onDeleteSchedule(timetableEntity: TimetableEntity) = viewModelScope.launch {
        deletedSchedule = timetableEntity
        dao.deleteSchedule(timetableEntity)
        uiEventChannel.send(UiEvent.ShowSnackBar(
            message = "Schedule deleted",
            actionLabel = "UNDO"
        ))
    }

    fun onUndoDeleteSchedule() = viewModelScope.launch {
        deletedSchedule?.let { dao.insertSchedule(it) }
    }
}