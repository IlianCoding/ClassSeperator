package com.app.assignmentandroidapplication.model

import com.app.assignmentandroidapplication.model.configuration.layoutType.LayoutType
import com.app.assignmentandroidapplication.model.configuration.sortingOptions.DifferentSortingOptions

data class Classroom (
    val id : String,
    val name : String,
    var layoutType: LayoutType,
    val desks : List<Desk>,
    var studentIds : MutableList<String>,
    var sortingOptions: DifferentSortingOptions = DifferentSortingOptions()
){
    fun updateLayoutStrategy(type: LayoutType){
        layoutType = type
    }

    fun updateSortingOptions(options: DifferentSortingOptions){
        sortingOptions = options
    }

    fun clearAssignments(){
        desks.forEach{ it.clearAssignment() }
        studentIds.clear()
    }

    fun getStudents(studentList: List<Student>): List<Student>{
        return studentIds.mapNotNull { studentId ->
            studentList.find { it.id == studentId }
        }
    }
}