package com.app.classseperation.model.configuration.layoutType

import com.app.classseperation.model.Position

data class ClassroomLayout(
    val type: LayoutType,
    val positions: List<Position>
)