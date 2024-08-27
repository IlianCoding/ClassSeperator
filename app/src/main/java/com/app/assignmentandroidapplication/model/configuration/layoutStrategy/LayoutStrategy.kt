package com.app.assignmentandroidapplication.model.configuration.layoutStrategy

import com.app.assignmentandroidapplication.model.Desk
import com.app.assignmentandroidapplication.model.Student
import com.app.assignmentandroidapplication.model.configuration.sortingOptions.DifferentSortingOptions

interface LayoutStrategy {
    fun assignStudents(students: List<Student>, desks: List<Desk>, sortingOptions: DifferentSortingOptions, groupSize: Int?): Boolean
}