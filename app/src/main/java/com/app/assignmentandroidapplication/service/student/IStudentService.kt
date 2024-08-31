package com.app.assignmentandroidapplication.service.student

import com.app.assignmentandroidapplication.model.Student

interface IStudentService {
    fun loadAllStudents(): List<Student>
    fun loadStudent(studentId: String): Student?
    fun saveStudent(student: Student)
    fun updateStudent(student: Student)
    fun deleteStudent(studentId: String)
}