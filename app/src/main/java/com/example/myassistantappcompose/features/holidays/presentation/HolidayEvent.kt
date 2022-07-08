package com.example.myassistantappcompose.features.holidays.presentation

sealed class HolidayEvent{
    object OnCountryNameChange: HolidayEvent()
    object OnCountryIsoChange: HolidayEvent()
    object OnYearChange: HolidayEvent()
    data class OnMonthChange(val month: Int): HolidayEvent()
}
