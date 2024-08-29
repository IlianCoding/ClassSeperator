package com.app.assignmentandroidapplication.model.configuration.sortingOptions

import kotlinx.serialization.Serializable

@Serializable
enum class SortingOption {
    AVOID_SAME_NATIONALITY,
    AVOID_ADJACENT_REPETITION,
    AVOID_SAME_SPOT_REPETITION
}