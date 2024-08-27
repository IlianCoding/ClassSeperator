package com.app.assignmentandroidapplication.model

data class Desk(
    val id : String,
    val position : Position,
    var assignedStudent: Student? = null,
    var previousStudent: Student? = null
){
    fun isAvailable(): Boolean {
        return assignedStudent == null
    }

    fun assignStudent(student: Student){
        previousStudent = assignedStudent
        assignedStudent = student
    }

    fun clearAssignment(){
        previousStudent = assignedStudent
        assignedStudent = null
    }
}