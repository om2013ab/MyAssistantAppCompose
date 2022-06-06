package com.example.myassistantappcompose.features.countries.presentation

import com.example.myassistantappcompose.features.countries.data.remote.response.CountriesResponse

data class CountriesState(
    val response: CountriesResponse? = null,
    val isLoading: Boolean = false
)
