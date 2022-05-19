package com.example.myassistantappcompose.features.timetable.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TimetableDao {

    @Query("SELECT * FROM timetable_table")
    fun getAllSchedules(): Flow<List<TimetableEntity>>

    @Query("SELECT * FROM timetable_table WHERE id = :id")
    suspend fun getScheduleById(id: Int): TimetableEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(timetableEntity: TimetableEntity)

    @Delete
    suspend fun deleteSchedule(timetableEntity: TimetableEntity)


}