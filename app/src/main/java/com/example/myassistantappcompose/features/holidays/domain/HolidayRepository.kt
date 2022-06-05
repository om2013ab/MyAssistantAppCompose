package com.example.myassistantappcompose.features.holidays.domain

import com.example.myassistantappcompose.core.util.Resource
import com.example.myassistantappcompose.features.holidays.data.remote.models.CountriesResponse
import com.example.myassistantappcompose.features.holidays.data.remote.HolidayApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HolidayRepository @Inject constructor(
    private val holidayApi: HolidayApi
) {
     suspend fun getAllCountries (): Resource<CountriesResponse>{
        return try {
            val response = holidayApi.getAllCountries()
            Resource.Success(response)
        } catch (e: IOException) {
            Resource.Error(e.localizedMessage ?: "Couldn't reach server")
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "Something went wrong")
        }
    }
}