package com.example.myassistantappcompose.features.holidays.data.mapper

import com.example.myassistantappcompose.features.holidays.data.local.CountriesEntity
import com.example.myassistantappcompose.features.holidays.data.remote.models.Countries
import com.example.myassistantappcompose.features.holidays.data.remote.models.CountriesInfo

fun CountriesEntity.toCountriesInfo(): CountriesInfo {
    return CountriesInfo(
        countryName = countryName,
        isoName = isoName,
        totalHolidays = totalHolidays
    )
}

fun CountriesInfo.toCountriesEntity(): CountriesEntity {
    return CountriesEntity(
        countryName = countryName,
        isoName = isoName,
        totalHolidays = totalHolidays
    )
}