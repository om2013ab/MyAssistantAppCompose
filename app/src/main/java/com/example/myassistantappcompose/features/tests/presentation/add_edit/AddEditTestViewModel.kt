package com.example.myassistantappcompose.features.tests.presentation.add_edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassistantappcompose.features.courses.data.CourseDao
import com.example.myassistantappcompose.features.tests.data.TestDao
import com.example.myassistantappcompose.features.tests.data.TestEntity
import com.example.myassistantappcompose.features.timetable.data.TimetableEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTestViewModel @Inject constructor(
    courseDao: CourseDao,
    savedStateHandle: SavedStateHandle,
    private val testDao: TestDao
): ViewModel() {

    val courses = courseDao.getAllCourses()

    var addEditState by  mutableStateOf(AddEditTestState())
        private set
    
    private var test by mutableStateOf<TestEntity?>(null)

    init {
        val testEntity =  savedStateHandle.get<TestEntity>("testEntity")
        if (testEntity != null) {
            viewModelScope.launch {
                test = testDao.getTestById(testEntity.id)
                test?.let {
                    addEditState = addEditState.copy(
                        selectedCode = it.courseCode,
                        selectedDate = it.date,
                        selectedTime = it.time,
                        enteredNote = it.note
                    )
                }
            }
        }
    }

    fun onAddEditEvent(event: AddEditTestEvent) {
        when(event) {
            is AddEditTestEvent.OnCourseCodeChange -> {
                addEditState = addEditState.copy(
                    selectedCode = event.code
                )
            }
            is AddEditTestEvent.OnDateChange -> {
                addEditState = addEditState.copy(
                    selectedDate = event.date
                )
            }
            is AddEditTestEvent.OnTimeChange -> {
                addEditState = addEditState.copy(
                    selectedTime = event.time
                )
            }
            is AddEditTestEvent.OnNoteChange -> {
                addEditState = addEditState.copy(
                    enteredNote = event.note
                )
            }
            AddEditTestEvent.OnAddTest -> viewModelScope.launch{
                val newTest = TestEntity(
                    courseCode = addEditState.selectedCode!!,
                    date = addEditState.selectedDate!!,
                    time = addEditState.selectedTime!!,
                    note = addEditState.enteredNote,
                    id = test?.id ?: 0
                )
                testDao.insertTest(newTest)
            }
        }
    }
}