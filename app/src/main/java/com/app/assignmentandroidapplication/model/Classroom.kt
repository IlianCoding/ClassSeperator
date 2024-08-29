package com.app.assignmentandroidapplication.model

import com.app.assignmentandroidapplication.model.configuration.layoutType.LayoutType
import com.app.assignmentandroidapplication.model.configuration.sortingOptions.DifferentSortingOptions
import kotlinx.serialization.Serializable

@Serializable
data class Classroom (
    val id : String,
    val name : String,
    var layoutType: LayoutType,
    val desks : List<Desk>,
    var students : MutableList<Student>,
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
        students.clear()
    }
}