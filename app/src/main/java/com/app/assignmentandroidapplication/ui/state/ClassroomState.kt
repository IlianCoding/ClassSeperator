package com.app.assignmentandroidapplication.ui.state

import com.app.assignmentandroidapplication.model.Classroom
import com.app.assignmentandroidapplication.model.Student

sealed class ClassroomState {
    object LoadingClassroom: ClassroomState()
    data class LoadedClassroom(val classroom: Classroom, val students: List<Student>): ClassroomState()
    data class ErrorClassroom(val errorMessage: String): ClassroomState()
}