package com.app.assignmentandroidapplication.service.student

import com.app.assignmentandroidapplication.data.student.IStudentRepository
import com.app.assignmentandroidapplication.model.Student
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudentService @Inject constructor(
    private val studentRepository: IStudentRepository
): IStudentService {

    override fun loadAllStudents(): List<Student> {
        return studentRepository.getAllStudents()
    }

    override fun loadStudent(studentId: String): Student? {
        return studentRepository.loadStudent(studentId)
    }

    override fun saveStudent(student: Student) {
        studentRepository.saveStudent(student)
    }

    override fun updateStudent(student: Student) {
        studentRepository.updateStudent(student)
    }

    override fun deleteStudent(studentId: String) {
        studentRepository.deleteStudent(studentId)
    }
}