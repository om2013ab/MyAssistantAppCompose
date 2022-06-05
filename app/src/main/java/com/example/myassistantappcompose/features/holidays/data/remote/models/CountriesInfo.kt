package com.example.myassistantappcompose.features.holidays.data.remote.models

import com.google.gson.annotations.SerializedName

data class CountriesInfo(
    @SerializedName("country_name")
    val countryName: String,

    @SerializedName("iso-3166")
    val isoName: String,

    @SerializedName("total_holidays")
    val totalHolidays: Int
)
