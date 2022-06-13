package com.example.myassistantappcompose.features.tests.presentation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassistantappcompose.core.presentation.UiEvent
import com.example.myassistantappcompose.features.destinations.AddEditTestScreenDestination
import com.example.myassistantappcompose.features.tests.data.TestDao
import com.example.myassistantappcompose.features.tests.data.TestEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalMaterialApi
@HiltViewModel
class TestViewModel @Inject constructor(
    private val testDao: TestDao
): ViewModel() {

    val tests = testDao.getAllTests()

    var testState by mutableStateOf(TestState())
        private set

    private val uiEventChannel = Channel<UiEvent>()
    val uiEvent = uiEventChannel.receiveAsFlow()

    fun onTestEvent(event: TestEvent) {
        when(event) {
            is TestEvent.OnTestClick -> {
                if (!testState.multiSelectionMode) {
                    viewModelScope.launch {
                        uiEventChannel.send(UiEvent.Navigate(AddEditTestScreenDestination(event.testEntity)))
                    }
                } else {
                    addOrRemoveSelectedTest(event.testEntity)
                }
            }
            is TestEvent.OnTestLongClick -> {
                testState = testState.copy(multiSelectionMode = !testState.multiSelectionMode)
                addOrRemoveSelectedTest(event.testEntity)
                if (!testState.multiSelectionMode) {
                    testState = testState.copy(selectedTests = emptyList())
                }

            }
            TestEvent.OnCloseMultiSelectionMode -> {
                testState = TestState()
            }
            TestEvent.OnShowDialog -> {
                testState = testState.copy(showDialog = true)
            }
            TestEvent.OnDeleteConfirmed -> viewModelScope.launch{
                testDao.deleteSelectedTests(testState.selectedTests)
                uiEventChannel.send(UiEvent.ShowSnackBar(
                    message = "${testState.selectedTests.size} tests deleted"
                ))
                testState = TestState()
            }
            TestEvent.OnDismissDialog -> {
                testState = testState.copy(showDialog = false)
            }

        }
    }

    private fun addOrRemoveSelectedTest(testEntity: TestEntity) {
        if (testState.selectedTests.contains(testEntity)) {
            val selectedTestUpdate = testState.selectedTests.toMutableList().apply {
                remove(testEntity)
            }
            testState = testState.copy(selectedTests = selectedTestUpdate)
            if (selectedTestUpdate.isEmpty()) {
                testState = testState.copy(multiSelectionMode = false)
            }
        } else {
            val selectedTestUpdate = testState.selectedTests.toMutableList().apply {
                add(testEntity)
            }
            testState = testState.copy(selectedTests = selectedTestUpdate)

        }
    }
}