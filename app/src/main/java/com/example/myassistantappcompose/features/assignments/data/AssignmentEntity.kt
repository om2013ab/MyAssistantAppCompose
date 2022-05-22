package com.example.myassistantappcompose.features.assignments.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assignment_table")
data class AssignmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val courseCode: String,
    val deadline: String,
    val description: String
)
