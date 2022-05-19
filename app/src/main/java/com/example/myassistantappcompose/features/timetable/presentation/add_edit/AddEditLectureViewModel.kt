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

    var addEditLectureState by mutableStateOf(AddEditLectureState())
        private set

    init {
        val id = savedStateHandle.get<TimetableEntity>("timetableEntity")?.id
        viewModelScope.launch {
            if (id != null) {
                schedule = timetableDao.getScheduleById(id)
                schedule?.let {
                    addEditLectureState = addEditLectureState.copy(
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

    fun onAddEditEvent(event: AddEditEvent) {
        when(event) {
            is AddEditEvent.OnCourseCodeChanged -> {
                addEditLectureState = addEditLectureState.copy(
                    selectedCode = event.code
                )
            }

            is AddEditEvent.OnTimeFromChanged ->{
                addEditLectureState = addEditLectureState.copy(
                    selectedTimeFrom = event.from
                )
            }
            is AddEditEvent.OnTimeToChanged -> {
                addEditLectureState = addEditLectureState.copy(
                    selectedTimeTo = event.to
                )
            }
            is AddEditEvent.OnVenueChanged -> {
                addEditLectureState = addEditLectureState.copy(
                    enteredVenue = event.venue
                )
            }
            AddEditEvent.OnAddSchedule -> viewModelScope.launch{
                val newSchedule = TimetableEntity(
                    selectedCode = addEditLectureState.selectedCode,
                    timeFrom = addEditLectureState.selectedTimeFrom,
                    timeTo = addEditLectureState.selectedTimeTo,
                    venue = addEditLectureState.enteredVenue,
                    dayIndex = dayIndex?.toInt() ?: addEditLectureState.selectedDay,
                    id = schedule?.id ?: 0
                )
                timetableDao.insertSchedule(newSchedule)
            }
        }
    }
}