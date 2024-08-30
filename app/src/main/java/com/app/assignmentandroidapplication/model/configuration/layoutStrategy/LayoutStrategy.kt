package com.app.assignmentandroidapplication.model.configuration.layoutStrategy

import com.app.assignmentandroidapplication.model.Classroom
import com.app.assignmentandroidapplication.model.Student

interface LayoutStrategy {
    fun assignStudents(classroom: Classroom): Boolean
}