package com.example.myassistantappcompose.features.countries.data.repository

import androidx.room.withTransaction
import com.example.myassistantappcompose.core.data.DataStoreManager
import com.example.myassistantappcompose.core.data.local.AppDatabase
import com.example.myassistantappcompose.features.countries.data.mapper.toCountriesEntity
import com.example.myassistantappcompose.features.countries.data.mapper.toCountriesInfo
import com.example.myassistantappcompose.core.presentation.util.Resource
import com.example.myassistantappcompose.features.countries.data.remote.response.CountriesResponse
import com.example.myassistantappcompose.core.data.remote.HolidayApi
import com.example.myassistantappcompose.features.countries.data.local.CountriesDao
import com.example.myassistantappcompose.features.countries.data.remote.response.Countries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryRepository @Inject constructor(
    private val holidayApi: HolidayApi,
    private val db: AppDatabase,
    private val dao: CountriesDao,
    private val dataStoreManager: DataStoreManager
) {

    suspend fun getCountries(
        fetchFromRemote: Boolean
    ): Flow<Resource<CountriesResponse>>{
        return flow {
            emit(Resource.Loading(true))

            //fetch from db
            val cachedCountries = dao.getAllCashedCountries()
            val countriesResponse = CountriesResponse(Countries(cachedCountries.map { it.toCountriesInfo() }))
            emit(Resource.Success(countriesResponse))

            val shouldJustLoadFromDb = cachedCountries.isNotEmpty() && !fetchFromRemote
            if (shouldJustLoadFromDb) {
                emit(Resource.Loading(false))
                return@flow
            }

            //fetch from remote and cache data then fetch from db
            try {
                val remoteCountries  = holidayApi.getAllCountries().response.countries
                db.withTransaction {
                    dao.clearCountries()
                    dao.insertCountries(remoteCountries.map { it.toCountriesEntity() })
                }
                val updatedCache = dao.getAllCashedCountries()
                val updatedCountriesResponse = CountriesResponse(Countries(updatedCache.map { it.toCountriesInfo() }))
                emit(Resource.Success(updatedCountriesResponse))
                emit(Resource.Loading(false))


            } catch (e: IOException){
                emit(Resource.Error(e.localizedMessage ?: "Couldn't reach server"))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "Something went wrong"))
            }
        }
    }

    suspend fun updateSelectedCountry(name: String, iso: String) {
        dataStoreManager.apply {
            saveCountryName(name)
            saveCountryIso(iso)
        }
    }
}