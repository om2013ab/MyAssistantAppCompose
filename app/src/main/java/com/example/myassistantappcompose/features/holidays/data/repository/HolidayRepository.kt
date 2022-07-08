package com.example.myassistantappcompose.features.holidays.data.repository

import androidx.room.withTransaction
import com.example.myassistantappcompose.core.data.DataStoreManager
import com.example.myassistantappcompose.core.data.local.AppDatabase
import com.example.myassistantappcompose.core.data.remote.HolidayApi
import com.example.myassistantappcompose.core.presentation.util.Resource
import com.example.myassistantappcompose.features.holidays.data.local.HolidaysDao
import com.example.myassistantappcompose.features.holidays.data.mapper.toHolidays
import com.example.myassistantappcompose.features.holidays.data.mapper.toHolidaysEntity
import com.example.myassistantappcompose.features.holidays.data.remote.response.HolidayResponse
import com.example.myassistantappcompose.features.holidays.data.remote.response.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HolidayRepository @Inject constructor(
    private val holidayApi: HolidayApi,
    private val db: AppDatabase,
    private val dao: HolidaysDao,
) {

    fun getAllHolidays(
        fetchFromRemote: Boolean,
        countryIso: String,
        year: Int,
    ): Flow<Resource<HolidayResponse>> {
        return flow {
            emit(Resource.Loading())

            val cachedHolidays = dao.getAllHolidays()
            val holidayResponse = HolidayResponse(Response(cachedHolidays.map { it.toHolidays() }))
            emit(Resource.Success(holidayResponse))

            val shouldLoadFromDb = cachedHolidays.isNotEmpty() && !fetchFromRemote
            if (shouldLoadFromDb) {
                emit(Resource.Loading(false))
                return@flow
            }

            try {
                val remoteHolidays = holidayApi.getAllHolidays(
                    countryIso = countryIso,
                    year = year
                ).response.holidays
                db.withTransaction {
                    dao.clearHolidays()
                    dao.insertHolidays(remoteHolidays.map { it.toHolidaysEntity() })
                }
                val updatedCache = dao.getAllHolidays()
                val updatedHolidayResponse = HolidayResponse(Response(updatedCache.map { it.toHolidays() }))
                emit(Resource.Success(updatedHolidayResponse))
                emit(Resource.Loading(false))
            } catch (e:IOException) {
                emit(Resource.Error(e.localizedMessage ?: "Couldn't reach server"))

            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "Something went wrong"))
            }
        }
//        return flow {
//            try {
//                emit(Resource.Loading())
//                val response = holidayApi.getAllHolidays(
//                    countryIso = countryIso,
//                    year = year,
//                )
//                emit(Resource.Success(response))
//            } catch (e: IOException) {
//                emit(Resource.Error(e.localizedMessage ?: "Couldn't reach server"))
//            } catch (e: HttpException) {
//                emit(Resource.Error(e.localizedMessage ?: "Something went wrong"))
//            }
//        }

    }
}