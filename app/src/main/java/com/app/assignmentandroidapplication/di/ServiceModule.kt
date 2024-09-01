package com.app.assignmentandroidapplication.di

import com.app.assignmentandroidapplication.data.classroom.IClassroomRepository
import com.app.assignmentandroidapplication.data.student.IStudentRepository
import com.app.assignmentandroidapplication.service.classroom.ClassroomService
import com.app.assignmentandroidapplication.service.classroom.IClassroomService
import com.app.assignmentandroidapplication.service.student.IStudentService
import com.app.assignmentandroidapplication.service.student.StudentService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideClassroomService(
        classroomRepository: IClassroomRepository,
        studentRepository: IStudentRepository
    ): IClassroomService{
        return ClassroomService(classroomRepository, studentRepository)
    }

    @Provides
    @Singleton
    fun provideStudentService(
        studentRepository: IStudentRepository
    ): IStudentService {
        return StudentService(studentRepository)
    }
}