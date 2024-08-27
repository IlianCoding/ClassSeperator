package com.app.assignmentandroidapplication.model.configuration.layoutStrategy

import com.app.assignmentandroidapplication.model.Desk
import com.app.assignmentandroidapplication.model.Student
import com.app.assignmentandroidapplication.model.configuration.sortingOptions.DifferentSortingOptions
import com.app.assignmentandroidapplication.model.configuration.sortingOptions.SortingOption
import com.app.assignmentandroidapplication.utils.LogHelper

class UShapeLayoutStrategy : LayoutStrategy {
    private val logger: LogHelper = LogHelper(this::class.java.simpleName)

    override fun assignStudents(students: List<Student>, desks: List<Desk>, sortingOptions: DifferentSortingOptions, groupSize: Int?): Boolean {
        logger.d("Starting student assignment using RowByRowLayoutStrategy.")
        val sortedDesks = desks.sortedWith(compareBy { it.position.row * 100 + it.position.column })
        val deskAssignments = sortedDesks.toMutableList()

        val success = backtrackAssignStudents(students, deskAssignments, sortingOptions)
        logger.i("Student assignment ${if (success) "succeeded" else "failed"} using RowByRowLayoutStrategy.")
        return success
    }

    private fun backtrackAssignStudents(
        students: List<Student>,
        desks: MutableList<Desk>,
        sortingOptions: DifferentSortingOptions,
        currentIndex: Int = 0
    ): Boolean {
        if (currentIndex >= students.size) {
            return true
        }

        val student = students[currentIndex]
        logger.d("Attempting to assign student ${student.fullName} (ID: ${student.id}) to a desk.")

        for (desk in desks) {
            if (desk.isAvailable() && isValidAssignment(student, desk, sortingOptions)) {
                desk.assignStudent(student)
                logger.d("Assigned student ${student.fullName} (ID: ${student.id}) to desk at position ${desk.position}.")

                if (backtrackAssignStudents(students, desks, sortingOptions, currentIndex + 1)) {
                    return true
                }

                desk.clearAssignment()
                logger.d("Backtracking: cleared assignment of student ${student.fullName} (ID: ${student.id}) from desk at position ${desk.position}.")
            }
        }
        return false
    }

    private fun isValidAssignment(student: Student, desk: Desk, sortingOptions: DifferentSortingOptions): Boolean {
        val selectedOptions = sortingOptions.selectedOptions

        val validByNationality = if (SortingOption.AVOID_SAME_NATIONALITY in selectedOptions) {
            desk.assignedStudent?.nationality != student.nationality
        } else {
            true
        }

        val validByRepetition = if (SortingOption.AVOID_ADJACENT_REPETITION in selectedOptions) {
            desk.previousStudent?.id != student.id
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
}