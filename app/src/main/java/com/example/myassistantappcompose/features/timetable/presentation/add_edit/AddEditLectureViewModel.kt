package com.example.myassistantappcompose.features.timetable.presentation.add_edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassistantappcompose.features.courses.data.CourseDao
import com.example.myassistantappcompose.features.timetable.data.TimetableDao
import com.example.myassistantappcompose.features.timetable.data.TimetableEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditLectureViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    courseDao: CourseDao,
    private val timetableDao: TimetableDao
): ViewModel() {

    val courses = courseDao.getAllCourses()
    private val dayIndex = savedStateHandle.get<String>("dayIndex")


    private var schedule by mutableStateOf<TimetableEntity?>(null)

    var addEditState by mutableStateOf(AddEditLectureState())
        private set

    init {
        val id = savedStateHandle.get<TimetableEntity>("timetableEntity")?.id
        viewModelScope.launch {
            if (id != null) {
                schedule = timetableDao.getScheduleById(id)
                schedule?.let {
                    addEditState = addEditState.copy(
                        selectedCode = it.selectedCode,
                        selectedTimeFrom = it.timeFrom,
                        selectedTimeTo = it.timeTo,
                        enteredVenue = it.venue,
                        selectedDay = it.dayIndex,
                    )
                }
            }
        }
    }

    fun onAddEditEvent(scheduleEvent: AddEditScheduleEvent) {
        when(scheduleEvent) {
            is AddEditScheduleEvent.OnCourseCodeChanged -> {
                addEditState = addEditState.copy(
                    selectedCode = scheduleEvent.code,
                )
            }

            is AddEditScheduleEvent.OnTimeFromChanged ->{
                addEditState = addEditState.copy(
                    selectedTimeFrom = scheduleEvent.from
                )
            }
            is AddEditScheduleEvent.OnTimeToChanged -> {
                addEditState = addEditState.copy(
                    selectedTimeTo = scheduleEvent.to
                )
            }
            is AddEditScheduleEvent.OnVenueChanged -> {
                addEditState = addEditState.copy(
                    enteredVenue = scheduleEvent.venue
                )
            }
            is AddEditScheduleEvent.OnAddSchedule -> viewModelScope.launch{
                val newSchedule = TimetableEntity(
                    selectedCode = addEditState.selectedCode!!,
                    timeFrom = addEditState.selectedTimeFrom!!,
                    timeTo = addEditState.selectedTimeTo!!,
                    venue = addEditState.enteredVenue,
                    color = scheduleEvent.color,
                    dayIndex = dayIndex?.toInt() ?: addEditState.selectedDay,
                    id = schedule?.id ?: 0
                )
                timetableDao.insertSchedule(newSchedule)
            }
        }
    }
}