package com.example.myassistantappcompose.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myassistantappcompose.features.courses.data.CourseDao
import com.example.myassistantappcompose.features.courses.data.CourseEntity

@Database(entities = [CourseEntity::class], exportSchema = false, version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun courseDao(): CourseDao
}