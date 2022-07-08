package com.example.myassistantappcompose.core.data.remote

import com.example.myassistantappcompose.BuildConfig.API_KEY
import com.example.myassistantappcompose.features.countries.data.remote.response.CountriesResponse
import com.example.myassistantappcompose.features.holidays.data.remote.response.HolidayResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HolidayApi {

    @GET("countries")
    suspend fun getAllCountries(
        @Query("api_key") apiKey: String = API_KEY,
    ): CountriesResponse

    @GET("holidays")
    suspend fun getAllHolidays(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("country") countryIso: String,
        @Query("year") year: Int,
    ): HolidayResponse
}