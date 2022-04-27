package com.example.myassistantappcompose.features.courses.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses_table")
data class CourseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val courseName: String,
    val courseCode: String,
    val courseHours: Int,
    val courseLecturer: String,
    val color: Int
)
