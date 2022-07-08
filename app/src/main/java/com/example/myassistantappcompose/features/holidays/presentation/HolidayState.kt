package com.example.myassistantappcompose.features.holidays.presentation

import com.example.myassistantappcompose.features.holidays.data.local.HolidaysEntity
import com.example.myassistantappcompose.features.holidays.data.remote.response.Holiday

data class HolidayState(
    val countryName: String? = null,
    val countryIso: String? = null,
    val year: Int? = null,
    val month: Int? = null,
    val holidays: List<HolidaysEntity>? = null,
    val isLoading: Boolean = false
)
