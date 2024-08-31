package com.app.assignmentandroidapplication.service.classroom

import com.app.assignmentandroidapplication.model.Classroom
import com.app.assignmentandroidapplication.model.Student

interface IClassroomService {
    fun loadAllClassrooms(): List<Classroom>
    fun loadClassroomDetails(classroomId: String): Pair<Classroom?, List<Student>>
    fun saveClassroom(classroom: Classroom)
    fun updateClassroom(classroom: Classroom)
    fun deleteClassroom(classroomId: String)
    suspend fun assigningStudents(classroom: Classroom)
}