package com.example.myassistantappcompose.features.tests.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "test_table")
data class TestEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val courseCode: String,
    val date: Date,
    val time: Date,
    val note: String
): Parcelable
