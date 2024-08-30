package com.app.assignmentandroidapplication.di

import android.content.Context
import com.app.assignmentandroidapplication.data.classroom.ClassroomRepository
import com.app.assignmentandroidapplication.data.classroom.IClassroomRepository
import com.app.assignmentandroidapplication.data.student.IStudentRepository
import com.app.assignmentandroidapplication.data.student.StudentRepository
import com.app.assignmentandroidapplication.utils.JsonWriterReader
import com.app.assignmentandroidapplication.utils.LogHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideJsonSerializer(): Json {
        return Json { ignoreUnknownKeys = true }
    }

    @Provides
    @Singleton
    fun provideJsonWriterReader(
        @ApplicationContext context: Context,
        jsonSerializer: Json
    ): JsonWriterReader {
        return JsonWriterReader(context, jsonSerializer)
    }

    @Provides
    @Singleton
    fun provideClassroomRepository(
        @ApplicationContext context: Context,
        jsonWriterReader: JsonWriterReader
    ): IClassroomRepository {
        return ClassroomRepository(context, jsonWriterReader)
    }

    @Provides
    @Singleton
    fun provideStudentRepository(
        @ApplicationContext context: Context,
        jsonWriterReader: JsonWriterReader
    ): IStudentRepository {
        return StudentRepository(context, jsonWriterReader)
    }
}