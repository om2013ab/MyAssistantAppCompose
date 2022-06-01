package com.example.myassistantappcompose.features.tests.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTest(testEntity: TestEntity)

    @Query("SELECT * FROM test_table")
    fun getAllTests(): Flow<List<TestEntity>>

    @Query("SELECT * FROM test_table WHERE id = :testId")
    suspend fun getTestById(testId: Int): TestEntity
}