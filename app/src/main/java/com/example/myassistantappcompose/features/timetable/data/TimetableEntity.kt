package com.example.myassistantappcompose.features.timetable.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "timetable_table")
data class TimetableEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val selectedCode: String,
    val timeFrom: String,
    val timeTo: String,
    val venue: String,
    val dayIndex: Int
): Parcelable
