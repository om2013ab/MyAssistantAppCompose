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
    fun onCourseCodeChanged(code: String) {
        addEditLectureState = addEditLectureState.copy(
            selectedCode = code
        )
    }

    fun onTimeFromChanged(timeFrom: String) {
        addEditLectureState = addEditLectureState.copy(
            selectedTimeFrom = timeFrom
        )
    }

    fun onTimeToChanged(timeTo: String) {
        addEditLectureState = addEditLectureState.copy(
            selectedTimeTo = timeTo
        )
    }

    fun onVenueChanged(venue: String) {
        addEditLectureState = addEditLectureState.copy(
             enteredVenue = venue
        )
    }

    fun onSelectedDayChanged(dayIndex: Int) {
        addEditLectureState = addEditLectureState.copy(
            selectedDay = dayIndex
        )
    }

    fun onAddLecture() = viewModelScope.launch {
        val newLecture = TimetableEntity(
            selectedCode = addEditLectureState.selectedCode,
            timeFrom = addEditLectureState.selectedTimeFrom,
            timeTo = addEditLectureState.selectedTimeTo,
            venue = addEditLectureState.enteredVenue,
            dayIndex = addEditLectureState.selectedDay,
            id = schedule?.id ?: 0
        )
        timetableDao.insertSchedule(newLecture)
    }

}