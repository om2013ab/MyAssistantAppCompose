package com.example.myassistantappcompose.features.tests.presentation

import com.example.myassistantappcompose.features.tests.data.TestEntity

data class TestState(
    val selectedTests: List<TestEntity> = emptyList(),
    val multiSelectionMode: Boolean = false,
    val showDialog: Boolean = false
)
