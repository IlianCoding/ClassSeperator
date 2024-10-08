package com.app.assignmentandroidapplication.model.configuration.sortingOptions

data class DifferentSortingOptions(
    val selectedOptions: List<SortingOption> = listOf(
        SortingOption.AVOID_SAME_NATIONALITY,
        SortingOption.AVOID_ADJACENT_REPETITION,
        SortingOption.AVOID_SAME_SPOT_REPETITION
    )
)