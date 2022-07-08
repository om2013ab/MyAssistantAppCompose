package com.example.myassistantappcompose.features.holidays.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "holidays_table")
data class HolidaysEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val description: String,
    val locations: String,
    val isoDate: String
)
