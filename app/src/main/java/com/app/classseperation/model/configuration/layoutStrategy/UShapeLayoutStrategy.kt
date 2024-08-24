package com.app.classseperation.model.configuration.layoutStrategy

import com.app.classseperation.model.Desk
import com.app.classseperation.model.Student
import com.app.classseperation.model.configuration.sortingOptions.DifferentSortingOptions

class UShapeLayoutStrategy : LayoutStrategy {
    override fun assignStudents(students: List<Student>, desks: List<Desk>, sortingOptions: DifferentSortingOptions): Boolean {
        return false
    }
}