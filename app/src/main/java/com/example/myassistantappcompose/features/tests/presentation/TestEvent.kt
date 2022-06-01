package com.example.myassistantappcompose.features.tests.presentation

import com.example.myassistantappcompose.features.tests.data.TestEntity

sealed class TestEvent{
    data class OnTestClick(val testEntity: TestEntity): TestEvent()
    data class OnTestLongClick(val testEntity: TestEntity): TestEvent()
    object OnCloseMultiSelectionMode: TestEvent()
}
