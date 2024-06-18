package com.app.classseperation.data

import com.app.classseperation.model.Student
import java.util.*

interface SeperatorRepository{
    suspend fun getStudents(): List<Student>
}

class SeperatorClassRepository : SeperatorRepository {

    override suspend fun getStudents(): List<Student> {
        return listOf(
            Student(
                id = "1",
                firstName = "Alice",
                lastName = "Smith",
                nationality = "USA",
                image = "No Image",
                age = Date(1995 - 1900, 5, 15),
                lastSittingSpot = 0,
                hasSpecialNeeds = false
            ),
            Student(
                id = "2",
                firstName = "Bob",
                lastName = "Johnson",
                nationality = "Canada",
                image = "No Image",
                age = Date(1994 - 1900, 8, 20),
                lastSittingSpot = 0,
                hasSpecialNeeds = true
            ),
            Student(
                id = "3",
                firstName = "Charlie",
                lastName = "Brown",
                nationality = "UK",
                image = "No Image",
                age = Date(1996 - 1900, 1, 10),
                lastSittingSpot = 0,
                hasSpecialNeeds = false
            ),
            Student(
                id = "4",
                firstName = "David",
                lastName = "Davis",
                nationality = "Germany",
                image = "No Image",
                age = Date(1997 - 1900, 10, 5),
                lastSittingSpot = 0,
                hasSpecialNeeds = false
            ),
            Student(
                id = "5",
                firstName = "Eve",
                lastName = "White",
                nationality = "France",
                image = "No Image",
                age = Date(1993 - 1900, 3, 25),
                lastSittingSpot = 0,
                hasSpecialNeeds = true
            ),
            Student(
                id = "6",
                firstName = "Fiona",
                lastName = "Miller",
                nationality = "Australia",
                image = "No Image",
                age = Date(1998 - 1900, 7, 12),
                lastSittingSpot = 0,
                hasSpecialNeeds = false
            ),
            Student(
                id = "7",
                firstName = "George",
                lastName = "Moore",
                nationality = "Japan",
                image = "No Image",
                age = Date(1992 - 1900, 9, 18),
                lastSittingSpot = 0,
                hasSpecialNeeds = false
            ),
            Student(
                id = "8",
                firstName = "Hannah",
                lastName = "Young",
                nationality = "China",
                image = "No Image",
                age = Date(1991 - 1900, 4, 30),
                lastSittingSpot = 0,
                hasSpecialNeeds = true
            ),
            Student(
                id = "9",
                firstName = "Isaac",
                lastName = "King",
                nationality = "India",
                image = "No Image",
                age = Date(1990 - 1900, 11, 8),
                lastSittingSpot = 0,
                hasSpecialNeeds = false
            ),
            Student(
                id = "10",
                firstName = "Julia",
                lastName = "Lee",
                nationality = "Brazil",
                image = "No Image",
                age = Date(1989 - 1900, 6, 3),
                lastSittingSpot = 0,
                hasSpecialNeeds = false
            )
        )
    }
}