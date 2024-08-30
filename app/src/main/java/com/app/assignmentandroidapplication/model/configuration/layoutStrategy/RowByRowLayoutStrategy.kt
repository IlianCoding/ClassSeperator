package com.app.assignmentandroidapplication.model.configuration.layoutStrategy

import com.app.assignmentandroidapplication.model.Classroom
import com.app.assignmentandroidapplication.model.Desk
import com.app.assignmentandroidapplication.model.Student
import com.app.assignmentandroidapplication.model.configuration.sortingOptions.DifferentSortingOptions
import com.app.assignmentandroidapplication.utils.LogHelper

class RowByRowLayoutStrategy : LayoutStrategy {
    private val logger: LogHelper = LogHelper(this::class.java.simpleName)

    override fun assignStudents(classroom: Classroom): Boolean {
        logger.d("Starting student assignment using RowByRowLayoutStrategy.")

        // Setting up the desks
        val sortedDesks = classroom.desks.sortedWith(compareBy { it.position.row * 100 + it.position.column })
        val deskAssignments = sortedDesks.toMutableList()

        return false
    }

    private fun backTrackingAssignmentProcess(
        students: List<Student>,
        desks: MutableList<Desk>,
        sortingOptions: DifferentSortingOptions
    ){

    }
}