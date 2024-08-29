package com.app.assignmentandroidapplication.model.configuration.layoutType

import kotlinx.serialization.Serializable

@Serializable
enum class LayoutType {
    ROW_BY_ROW,
    U_SHAPE,
    LAB_LAYOUT,
    GROUPED_LAYOUT
}