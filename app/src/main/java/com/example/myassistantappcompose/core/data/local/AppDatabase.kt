package com.example.myassistantappcompose.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myassistantappcompose.features.assignments.data.AssignmentDao
import com.example.myassistantappcompose.features.assignments.data.AssignmentEntity
import com.example.myassistantappcompose.features.courses.data.CourseDao
import com.example.myassistantappcompose.features.courses.data.CourseEntity
import com.example.myassistantappcompose.features.countries.data.local.CountriesDao
import com.example.myassistantappcompose.features.countries.data.local.CountriesEntity
import com.example.myassistantappcompose.features.tests.data.TestDao
import com.example.myassistantappcompose.features.tests.data.TestEntity
import com.example.myassistantappcompose.features.timetable.data.TimetableDao
import com.example.myassistantappcompose.features.timetable.data.TimetableEntity

@TypeConverters(TypeConverter::class)
@Database(
    entities = [
        CourseEntity::class,
        TimetableEntity::class,
        AssignmentEntity::class,
        TestEntity::class,
        CountriesEntity::class],
    exportSchema = false, version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun courseDao(): CourseDao
    abstract fun timetableDao(): TimetableDao
    abstract fun assignmentDao(): AssignmentDao
    abstract fun testDao(): TestDao
    abstract fun countriesDao(): CountriesDao
}