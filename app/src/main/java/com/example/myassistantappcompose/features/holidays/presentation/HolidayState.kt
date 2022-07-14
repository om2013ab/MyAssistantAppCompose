package com.example.myassistantappcompose.features.holidays.presentation

import com.example.myassistantappcompose.features.holidays.data.local.HolidaysEntity
import com.example.myassistantappcompose.features.holidays.data.remote.response.Holiday

data class HolidayState(
    val countryName: String? = null,
    val countryIso: String? = null,
    val year: Int? = null,
    val monthIndex: Int = 0,
    val holidays: List<HolidaysEntity>? = null,
    val isLoading: Boolean = false,
    val showDialog: Boolean = false,
    val clickedHoliday: HolidaysEntity? = null
)
