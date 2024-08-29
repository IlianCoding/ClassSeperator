package com.app.assignmentandroidapplication.data.student

import com.app.assignmentandroidapplication.model.Student

interface IStudentRepository {
    fun saveStudent(student: Student)
    fun loadStudent(id: String): Student?
    fun updateStudent(student: Student)
    fun deleteStudent(id: String)
    fun getAllStudents(): List<Student>
}