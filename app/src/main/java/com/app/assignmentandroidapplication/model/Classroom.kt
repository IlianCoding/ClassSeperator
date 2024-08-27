package com.app.assignmentandroidapplication.model

import com.app.assignmentandroidapplication.model.configuration.layoutStrategy.LayoutStrategy
import com.app.assignmentandroidapplication.model.configuration.sortingOptions.DifferentSortingOptions

data class Classroom (
    val id : String,
    val name : String,
    var layoutStrategy: LayoutStrategy,
    val desks : List<Desk>,
    var students : MutableList<Student>,
    var sortingOptions: DifferentSortingOptions = DifferentSortingOptions()
){
    fun updateLayoutStrategy(strategy: LayoutStrategy){
        layoutStrategy = strategy
    }

    fun updateSortingOptions(options: DifferentSortingOptions){
        sortingOptions = options
    }

    fun clearAssignments(){
        desks.forEach{ it.clearAssignment() }
        students.clear()
    }
}