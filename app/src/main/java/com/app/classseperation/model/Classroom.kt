package com.app.classseperation.model

import com.app.classseperation.model.configuration.layoutStrategy.LayoutStrategy
import com.app.classseperation.model.configuration.sortingOptions.DifferentSortingOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

data class Classroom (
    val id : String,
    val name : String,
    var layoutStrategy: LayoutStrategy,
    val desks : List<Desk>,
    var students : MutableList<Student>,
    var sortingOptions: DifferentSortingOptions = DifferentSortingOptions()
){
    fun setLayoutStrategy(strategy: LayoutStrategy){
        layoutStrategy = strategy
    }

    fun setSortingOptions(options: DifferentSortingOptions){
        sortingOptions = options
    }

    fun clearAssignments(){
        desks.forEach{ it.clearAssignment() }
        students.clear()
    }
}