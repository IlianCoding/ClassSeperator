package com.app.assignmentandroidapplication.model

import java.time.LocalDate
import java.time.Period

data class Student(
    val id: String,
    val firstName: String,
    val lastName: String,
    val nationality: String,
    val imageUri: String,
    val birthDate: LocalDate,
    var hasSpecialNeeds: Boolean
) {
    val age : Int
        get() = Period.between(birthDate, LocalDate.now()).years

    val fullName : String
        get() = "$firstName $lastName"
}