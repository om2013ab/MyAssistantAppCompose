package com.example.myassistantappcompose.features.timetable.presentation.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassistantappcompose.features.courses.data.CourseDao
import com.example.myassistantappcompose.features.timetable.data.TimetableDao
import com.example.myassistantappcompose.features.timetable.data.TimetableEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddLectureViewModel @Inject constructor(
    private val courseDao: CourseDao,
    private val timetableDao: TimetableDao
): ViewModel() {

    val courses = courseDao.getAllCourses()

    var addLectureState by mutableStateOf(AddLectureState())
        private set


    fun onCourseCodeChanged(code: String) {
        addLectureState = addLectureState.copy(
            selectedCode = code
        )
    }

    fun onTimeFromChanged(timeFrom: String) {
        addLectureState = addLectureState.copy(
            selectedTimeFrom = timeFrom
        )
    }

    fun onTimeToChanged(timeTo: String) {
        addLectureState = addLectureState.copy(
            selectedTimeTo = timeTo
        )
    }

    fun onVenueChanged(venue: String) {
        addLectureState = addLectureState.copy(
             enteredVenue = venue
        )
    }

    fun onSelectedDayChanged(dayIndex: Int) {
        addLectureState = addLectureState.copy(
            selectedDay = dayIndex
        )
    }

    fun onAddLecture() = viewModelScope.launch {
        val newLecture = TimetableEntity(
            selectedCode = addLectureState.selectedCode,
            timeFrom = addLectureState.selectedTimeFrom,
            timeTo = addLectureState.selectedTimeTo,
            venue = addLectureState.enteredVenue,
            dayIndex = addLectureState.selectedDay,
            id = 0
        )
        timetableDao.insertTimetable(newLecture)
    }

}