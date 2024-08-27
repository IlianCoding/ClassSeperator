package com.app.assignmentandroidapplication.model.configuration.layoutType

import com.app.assignmentandroidapplication.model.Position

data class ClassroomLayout(
    val type: LayoutType,
    val positions: List<Position>
)