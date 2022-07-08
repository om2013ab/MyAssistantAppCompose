package com.example.myassistantappcompose.features.tests.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface TestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTest(testEntity: TestEntity)

    @Query("SELECT * FROM test_table")
    fun getAllTests(): Flow<List<TestEntity>>

    @Query("SELECT * FROM test_table WHERE id = :testId")
    suspend fun getTestById(testId: Int): TestEntity

    @Delete
    suspend fun deleteSelectedTests(selectedTests: List<TestEntity>)
}