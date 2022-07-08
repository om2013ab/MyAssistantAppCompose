package com.example.myassistantappcompose.features.holidays.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HolidaysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHolidays(holidays: List<HolidaysEntity>)

    @Query("SELECT * FROM holidays_table")
    suspend fun getAllHolidays(): List<HolidaysEntity>

    @Query("DELETE FROM holidays_table ")
    suspend fun clearHolidays()
}