package com.example.myassistantappcompose.core.data.local

import androidx.room.TypeConverter
import java.util.*

class TypeConverter {

    @TypeConverter
    fun fromLongToDate (date: Long?): Date? = date?.let { Date(it) }

    @TypeConverter
    fun fromDateToLong (data: Date?): Long? = data?.time
}