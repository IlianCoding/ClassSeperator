package com.app.assignmentandroidapplication.model

data class Desk(
    val id : String,
    val position : Position,
    var assignedStudentId: String? = null,
    var previousStudentId: String? = null
){
    fun isAvailable(): Boolean {
        return assignedStudentId == null
    }

    fun assignStudent(student: Student){
        previousStudentId = assignedStudentId
        assignedStudentId = student.id
    }

    fun clearAssignment(){
        previousStudentId = assignedStudentId
        assignedStudentId = null
    }

    fun getAssignedStudent(studentMap: Map<String, Student>): Student? {
        return assignedStudentId?.let { studentMap[it] }
    }

    fun getPreviousStudent(studentMap: Map<String, Student>): Student? {
        return previousStudentId?.let { studentMap[it] }
    }
}