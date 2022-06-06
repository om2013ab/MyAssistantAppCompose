package com.example.myassistantappcompose.features.countries.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CountriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countries: List<CountriesEntity>)

    @Query("DELETE FROM countries_table")
    suspend fun clearCountries()

    @Query("SELECT * FROM countries_table")
    suspend fun getAllCashedCountries(): List<CountriesEntity>
}