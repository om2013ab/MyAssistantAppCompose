package com.example.myassistantappcompose.di

import android.content.Context
import androidx.room.Room
import com.example.myassistantappcompose.core.data.AppDatabase
import com.example.myassistantappcompose.features.assignments.data.AssignmentDao
import com.example.myassistantappcompose.features.courses.data.CourseDao
import com.example.myassistantappcompose.features.tests.data.TestDao
import com.example.myassistantappcompose.features.timetable.data.TimetableDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "my_assistant.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCourseDao(db: AppDatabase): CourseDao {
        return db.courseDao()
    }

    @Provides
    @Singleton
    fun provideTimetableDao(db: AppDatabase): TimetableDao {
        return db.timetableDao()
    }

    @Provides
    @Singleton
    fun provideAssignmentDao(db: AppDatabase): AssignmentDao {
        return db.assignmentDao()
    }

    @Provides
    @Singleton
    fun provideTestDao(db: AppDatabase): TestDao {
        return db.testDao()
    }
}