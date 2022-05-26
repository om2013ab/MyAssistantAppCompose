package com.example.myassistantappcompose.features.assignments.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AssignmentDao {

    @Query("SELECT * FROM assignment_table")
    fun getAllAssignments(): Flow<List<AssignmentEntity>>

    @Query("SELECT * FROM assignment_table WHERE id =:assignmentId")
    suspend fun getAssignmentById(assignmentId: Int): AssignmentEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssignment(assignmentEntity: AssignmentEntity)

    @Delete
    suspend fun deleteSelectedAssignments(assignmentEntity: List<AssignmentEntity>)
}