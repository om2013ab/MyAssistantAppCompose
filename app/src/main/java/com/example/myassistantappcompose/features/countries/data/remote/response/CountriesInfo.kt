package com.example.myassistantappcompose.features.countries.data.remote.response

import com.google.gson.annotations.SerializedName

data class CountriesInfo(
    @SerializedName("country_name")
    val countryName: String,

    @SerializedName("iso-3166")
    val isoName: String,

    @SerializedName("total_holidays")
    val totalHolidays: Int
)
