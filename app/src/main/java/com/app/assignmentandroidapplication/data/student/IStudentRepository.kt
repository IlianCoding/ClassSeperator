package com.app.assignmentandroidapplication.data.student

import android.graphics.Bitmap
import com.app.assignmentandroidapplication.model.Student

interface IStudentRepository {
    fun saveStudent(student: Student)
    fun loadStudent(id: String): Student?
    fun updateStudent(student: Student)
    fun deleteStudent(id: String)
    fun getStudentsByIds(studentIds: List<String>): List<Student>
    fun getAllStudents(): List<Student>
    fun saveStudentImage(student: Student, image: Bitmap)
    fun loadStudentImage(student: Student): Bitmap?
}