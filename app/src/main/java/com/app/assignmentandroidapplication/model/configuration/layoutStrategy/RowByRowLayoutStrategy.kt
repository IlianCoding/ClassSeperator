package com.app.assignmentandroidapplication.model.configuration.layoutStrategy

import com.app.assignmentandroidapplication.model.Desk
import com.app.assignmentandroidapplication.model.Position
import com.app.assignmentandroidapplication.model.Student
import com.app.assignmentandroidapplication.model.configuration.sortingOptions.DifferentSortingOptions
import com.app.assignmentandroidapplication.model.configuration.sortingOptions.SortingOption
import com.app.assignmentandroidapplication.utils.LogHelper
import kotlin.math.abs

class RowByRowLayoutStrategy : LayoutStrategy {
    private val logger: LogHelper = LogHelper(this::class.java.simpleName)

    override fun assignStudents(students: List<Student>, desks: List<Desk>, sortingOptions: DifferentSortingOptions, groupSize: Int?): Boolean {
        logger.d("Starting student assignment using RowByRowLayoutStrategy.")
        val sortedDesks = desks.sortedWith(compareBy { it.position.row * 100 + it.position.column })
        val deskAssignments = sortedDesks.toMutableList()

        // Initialize a local list for unassigned students
        val unassignedStudents = mutableListOf<Student>()

        // Start the assignment process
        var success = backtrackAssignStudents(students, deskAssignments, sortingOptions, unassignedStudents)
        if (success) {
            logger.i("First student assignment succeeded using RowByRowLayoutStrategy.")
            return success
        } else {
            logger.d("Initial assignment failed, attempting to reassign unplaced students.")
            success = reassignUnassignedStudents(unassignedStudents, deskAssignments, sortingOptions)

            logger.i("Reassignment ${if (success) "succeeded" else "failed"}.")
            return success
        }
    }

    private fun backtrackAssignStudents(
        students: List<Student>,
        desks: MutableList<Desk>,
        sortingOptions: DifferentSortingOptions,
        unassignedStudents: MutableList<Student>,
        currentIndex: Int = 0
    ): Boolean {
        if (currentIndex >= students.size) {
            return true
        }

        val student = students[currentIndex]
        logger.d("Attempting to assign student ${student.fullName} (ID: ${student.id}) to a desk.")

        for (desk in desks) {
            if (desk.isAvailable() && isValidAssignment(student, desk, desks, sortingOptions)) {
                desk.assignStudent(student)
                logger.d("Assigned student ${student.fullName} (ID: ${student.id}) to desk at position ${desk.position}.")

                if (backtrackAssignStudents(students, desks, sortingOptions, unassignedStudents, currentIndex + 1)) {
                    return true
                }

                desk.clearAssignment()
                logger.d("Backtracking: cleared assignment of student ${student.fullName} (ID: ${student.id}) from desk at position ${desk.position}.")
            }
        }

        // If no valid assignment was found, add the student to the unassigned list
        unassignedStudents.add(student)
        return false
    }

    private fun reassignUnassignedStudents(
        unassignedStudents: List<Student>,
        desks: MutableList<Desk>,
        sortingOptions: DifferentSortingOptions
    ): Boolean {
        val desksCopy = desks.toMutableList()

        // Try to reassign unassigned students
        return backtrackAssignStudents(unassignedStudents, desksCopy, sortingOptions, mutableListOf())
    }

    private fun isValidAssignment(student: Student, desk: Desk, desks: List<Desk>, sortingOptions: DifferentSortingOptions): Boolean {
        val selectedOptions = sortingOptions.selectedOptions

        val validByNationality = if (SortingOption.AVOID_SAME_NATIONALITY in selectedOptions) {
            val adjacentDesks = desks.filter { isAdjacent(desk.position, it.position) }
            val adjacentStudents = adjacentDesks.mapNotNull { it.assignedStudent }
            adjacentStudents.all { it.nationality != student.nationality }
        } else {
            true
        }

        val validByRepetition = if (SortingOption.AVOID_ADJACENT_REPETITION in selectedOptions) {
            val adjacentDesks = desks.filter { isAdjacent(desk.position, it.position) }
            val adjacentStudents = adjacentDesks.mapNotNull { it.previousStudent }
            adjacentStudents.all { it.id != student.id }
        } else {
            true
        }

        val validBySpotRepetition = if (SortingOption.AVOID_SAME_SPOT_REPETITION in selectedOptions) {
            desk.previousStudent?.id != student.id
        } else {
            true
        }

        return validByNationality && validByRepetition && validBySpotRepetition
    }

    private fun isAdjacent(pos1: Position, pos2: Position): Boolean {
        val rowDiff = abs(pos1.row - pos2.row)
        val colDiff = abs(pos1.column - pos2.column)
        return (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1)
    }
}