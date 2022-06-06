package com.example.myassistantappcompose.features.countries.data.mapper

import com.example.myassistantappcompose.features.countries.data.local.CountriesEntity
import com.example.myassistantappcompose.features.countries.data.remote.response.CountriesInfo

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