package com.example.myassistantappcompose.features.timetable.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "timetable_table")
data class TimetableEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val selectedCode: String,
    val timeFrom: Date,
    val timeTo: Date,
    val venue: String,
    val dayIndex: Int,
    val color: Int
): Parcelable
