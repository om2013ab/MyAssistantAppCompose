package com.example.myassistantappcompose.features.holidays.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries_table")
data class CountriesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val countryName: String,
    val isoName:String,
    val totalHolidays: Int
)
