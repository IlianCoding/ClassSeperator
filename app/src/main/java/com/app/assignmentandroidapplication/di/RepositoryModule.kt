package com.app.assignmentandroidapplication.di

import android.content.Context
import com.app.assignmentandroidapplication.data.classroom.ClassroomRepository
import com.app.assignmentandroidapplication.data.classroom.IClassroomRepository
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
    fun provideClassroomRepository(
        @ApplicationContext context: Context,
        jsonSerializer: Json
    ): IClassroomRepository {
        return ClassroomRepository(context, jsonSerializer)
    }
}