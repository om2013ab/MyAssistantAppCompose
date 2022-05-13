package com.example.myassistantappcompose.features.courses.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Query("SELECT * FROM courses_table")
    fun getAllCourses(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses_table WHERE id =:courseId")
    suspend fun getCourseById(courseId: Int): CourseEntity

    @Insert(entity = CourseEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(courseEntity: CourseEntity)

    @Delete
    suspend fun deleteCourse(courseEntity: CourseEntity)


}