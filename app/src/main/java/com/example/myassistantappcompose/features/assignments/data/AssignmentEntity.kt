package com.example.myassistantappcompose.features.assignments.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "assignment_table")
data class AssignmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val courseCode: String,
    val deadline: Date,
    val description: String
): Parcelable
