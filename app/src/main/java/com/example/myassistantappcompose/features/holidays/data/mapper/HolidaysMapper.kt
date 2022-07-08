package com.example.myassistantappcompose.features.holidays.data.mapper

import com.example.myassistantappcompose.features.holidays.data.local.HolidaysEntity
import com.example.myassistantappcompose.features.holidays.data.remote.response.Date
import com.example.myassistantappcompose.features.holidays.data.remote.response.DatetimeX
import com.example.myassistantappcompose.features.holidays.data.remote.response.Holiday

fun HolidaysEntity.toHolidays(): Holiday {
    return Holiday(
        name = name,
        description = description,
        locations = locations,
        date = Date(isoDate)
    )
}

fun Holiday.toHolidaysEntity(): HolidaysEntity {
    return HolidaysEntity(
        name = name,
        description = description,
        locations = locations,
        isoDate = date.isoDate
    )
}