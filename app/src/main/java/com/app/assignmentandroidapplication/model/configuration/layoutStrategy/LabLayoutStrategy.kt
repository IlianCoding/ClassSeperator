package com.app.assignmentandroidapplication.model.configuration.layoutStrategy

import com.app.assignmentandroidapplication.model.Desk
import com.app.assignmentandroidapplication.model.Student
import com.app.assignmentandroidapplication.model.configuration.sortingOptions.DifferentSortingOptions
import com.app.assignmentandroidapplication.model.configuration.sortingOptions.SortingOption
import com.app.assignmentandroidapplication.utils.LogHelper

class LabLayoutStrategy : LayoutStrategy {
    private val logger: LogHelper = LogHelper(this::class.java.simpleName)

    override fun assignStudents(students: List<Student>, desks: List<Desk>, sortingOptions: DifferentSortingOptions, groupSize: Int?): Boolean {
        logger.d("Starting LabLayoutStrategy with groupSize = $groupSize")
        val groupedDesks = groupDesks(desks, groupSize)
        return assignStudentsInGroups(students, groupedDesks, sortingOptions)
    }

    private fun groupDesks(desks: List<Desk>, groupSize: Int?): List<List<Desk>> {
        logger.d("Grouping desks with groupSize = $groupSize")
        val groupedDesks = mutableListOf<List<Desk>>()
        var currentGroup = mutableListOf<Desk>()

        for (desk in desks) {
            currentGroup.add(desk)
            if (currentGroup.size == groupSize) {
                groupedDesks.add(currentGroup)
                currentGroup = mutableListOf()
            }
        }

        if (currentGroup.isNotEmpty()) {
            groupedDesks.add(currentGroup)
        }

        logger.d("Finished grouping desks. Total groups formed: ${groupedDesks.size}")
        return groupedDesks
    }

    private fun assignStudentsInGroups(students: List<Student>, groupedDesks: List<List<Desk>>, sortingOptions: DifferentSortingOptions): Boolean {
        logger.d("Assigning students in groups. Total groups: ${groupedDesks.size}")
        val studentQueue = students.toMutableList()

        for (group in groupedDesks) {
            if (!assignGroup(studentQueue, group, sortingOptions)) {
                logger.e("Failed to assign students in one of the groups")
                return false
            }
        }
        val success = studentQueue.isEmpty()
        if (success) {
            logger.d("All students assigned successfully")
        } else {
            logger.e("Some students could not be assigned")
        }
        return success
    }

    private fun assignGroup(students: MutableList<Student>, group: List<Desk>, sortingOptions: DifferentSortingOptions): Boolean {
        logger.d("Attempting to assign group of desks")
        return backtrackAssignGroup(students, group, sortingOptions)
    }

    private fun backtrackAssignGroup(students: MutableList<Student>, group: List<Desk>, sortingOptions: DifferentSortingOptions, currentIndex: Int = 0): Boolean {
        if (currentIndex >= students.size || group.isEmpty()) {
            return true
        }

        val student = students[currentIndex]
        logger.d("Assigning student ${student.fullName} to a desk in the group")

        for (desk in group) {
            if (desk.isAvailable() && isValidAssignment(student, desk, sortingOptions)) {
                desk.assignStudent(student)
                logger.d("Assigned ${student.fullName} to desk ${desk.id}")

                if (backtrackAssignGroup(students, group, sortingOptions, currentIndex + 1)) {
                    students.removeAt(currentIndex)
                    return true
                }
                desk.clearAssignment()
                logger.d("Cleared assignment for desk ${desk.id} as backtracking failed")
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

        val isValid = validByNationality && validByRepetition && validBySpotRepetition
        logger.d("Validation result for student ${student.fullName} at desk ${desk.id}: $isValid")
        return isValid
    }
}