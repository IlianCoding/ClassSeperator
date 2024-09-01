package com.app.assignmentandroidapplication.di

import android.content.Context
import com.app.assignmentandroidapplication.data.classroom.ClassroomRepository
import com.app.assignmentandroidapplication.data.classroom.IClassroomRepository
import com.app.assignmentandroidapplication.data.student.IStudentRepository
import com.app.assignmentandroidapplication.data.student.StudentRepository
import com.app.assignmentandroidapplication.utils.LogHelper
import com.app.assignmentandroidapplication.utils.gson.GsonWriterReader
import com.google.gson.Gson
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
    fun provideJsonSerializer(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideJsonWriterReader(
        @ApplicationContext context: Context,
        gson: Gson
    ): GsonWriterReader {
        return GsonWriterReader(context, gson)
    }

    @Provides
    @Singleton
    fun provideClassroomRepository(
        @ApplicationContext context: Context,
        gsonWriterReader: GsonWriterReader
    ): IClassroomRepository {
        return ClassroomRepository(context, gsonWriterReader)
    }

    @Provides
    @Singleton
    fun provideStudentRepository(
        @ApplicationContext context: Context,
        gsonWriterReader: GsonWriterReader
    ): IStudentRepository {
        return StudentRepository(context, gsonWriterReader)
    }
}