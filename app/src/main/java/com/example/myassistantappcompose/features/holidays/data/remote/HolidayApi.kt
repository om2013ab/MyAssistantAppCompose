package com.example.myassistantappcompose.features.holidays.data.remote

import com.example.myassistantappcompose.BuildConfig.API_KEY
import com.example.myassistantappcompose.features.holidays.data.remote.models.CountriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HolidayApi {

    @GET("countries")
    suspend fun getAllCountries(
        @Query("api_key") apiKey: String = API_KEY,
    ): CountriesResponse
}