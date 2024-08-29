package com.app.assignmentandroidapplication.model

import com.app.assignmentandroidapplication.utils.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.Period

@Serializable
data class Student(
    val id: String,
    val firstName: String,
    val lastName: String,
    val nationality: String,
    val imageUri: String,
    @Serializable(with = LocalDateSerializer::class)
    val birthDate: LocalDate,
    var hasSpecialNeeds: Boolean
) {
    val age : Int
        get() = Period.between(birthDate, LocalDate.now()).years

    val fullName : String
        get() = "$firstName $lastName"
}