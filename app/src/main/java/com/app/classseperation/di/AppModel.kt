package com.app.classseperation.di

import com.app.classseperation.data.SeperatorClassRepository
import com.app.classseperation.data.SeperatorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModel {

    @Provides
    @Singleton
    fun provideRepository(): SeperatorRepository {
        return SeperatorClassRepository()
    }
}