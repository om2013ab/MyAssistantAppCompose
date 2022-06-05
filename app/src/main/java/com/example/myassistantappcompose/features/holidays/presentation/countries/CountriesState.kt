package com.example.myassistantappcompose.features.holidays.presentation.countries

import com.example.myassistantappcompose.features.holidays.data.remote.models.CountriesResponse

data class CountriesState(
    val response: CountriesResponse? = null,
    val isLoading: Boolean = false
)
