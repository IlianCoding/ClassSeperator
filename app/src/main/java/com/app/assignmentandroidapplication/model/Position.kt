package com.app.assignmentandroidapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class Position(
    val row : Int,
    val column : Int
)