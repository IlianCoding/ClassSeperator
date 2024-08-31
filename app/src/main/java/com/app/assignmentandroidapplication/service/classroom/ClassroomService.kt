package com.app.assignmentandroidapplication.service.classroom

import com.app.assignmentandroidapplication.data.classroom.ClassroomRepository
import com.app.assignmentandroidapplication.data.student.StudentRepository
import com.app.assignmentandroidapplication.model.Classroom
import com.app.assignmentandroidapplication.model.Student
import com.app.assignmentandroidapplication.model.configuration.layoutStrategy.LayoutStrategy
import com.app.assignmentandroidapplication.model.configuration.layoutType.LayoutType
import com.app.assignmentandroidapplication.utils.LogHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClassroomService @Inject constructor(
    private val classroomRepository: ClassroomRepository,
    private val studentRepository: StudentRepository
): IClassroomService {
    private val logger: LogHelper = LogHelper(this::class.java.simpleName)

    override fun loadAllClassrooms(): List<Classroom> {
        return classroomRepository.loadAllClassrooms()
    }

    override fun loadClassroomDetails(classroomId: String): Pair<Classroom?, List<Student>> {
        val classroom = classroomRepository.loadClassroom(classroomId)

        return if (classroom != null){
            val students = studentRepository.getStudentsByIds(classroom.studentIds)
            Pair(classroom, students)
        } else {
            Pair(null, emptyList())
        }
    }

    override fun saveClassroom(classroom: Classroom) {
        classroomRepository.saveClassroom(classroom)
    }

    override fun updateClassroom(classroom: Classroom) {
        classroomRepository.updateClassroom(classroom)
    }

    override fun deleteClassroom(classroomId: String) {
        classroomRepository.deleteClassroom(classroomId)
    }

    override suspend fun assigningStudents(classroom: Classroom) {
        logger.d("Initialising the student assignment process")
    }
}