package com.app.assignmentandroidapplication.data

import com.app.assignmentandroidapplication.model.Student

interface SeperatorRepository{
    suspend fun getStudents(): List<Student>
}