package com.app.classseperation.model

import java.util.Date

data class Student(
    val id: String,
    val firstName: String,
    val lastName: String,
    val nationality: String,
    val image: String,
    val age: Date,
    var lastSittingSpot: Int,
    var hasSpecialNeeds: Boolean
)