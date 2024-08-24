package com.app.classseperation.data

import com.app.classseperation.model.Student
import java.time.LocalDate
import java.time.Month

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
                age = LocalDate.of(1995, Month.JUNE, 15),
                lastSittingSpot = 0,
                hasSpecialNeeds = false
            ),
            Student(
                id = "2",
                firstName = "Bob",
                lastName = "Johnson",
                nationality = "Canada",
                image = "No Image",
                age = LocalDate.of(1994, Month.SEPTEMBER, 20),
                lastSittingSpot = 0,
                hasSpecialNeeds = true
            ),
            Student(
                id = "3",
                firstName = "Charlie",
                lastName = "Brown",
                nationality = "Canada",
                image = "No Image",
                age = LocalDate.of(1996, Month.FEBRUARY, 10),
                lastSittingSpot = 0,
                hasSpecialNeeds = false
            ),
            Student(
                id = "4",
                firstName = "David",
                lastName = "Davis",
                nationality = "Germany",
                image = "No Image",
                age = LocalDate.of(1997, Month.NOVEMBER, 5),
                lastSittingSpot = 0,
                hasSpecialNeeds = false
            ),
            Student(
                id = "5",
                firstName = "Eve",
                lastName = "White",
                nationality = "France",
                image = "No Image",
                age = LocalDate.of(1993, Month.APRIL, 25),
                lastSittingSpot = 0,
                hasSpecialNeeds = true
            ),
            Student(
                id = "6",
                firstName = "Fiona",
                lastName = "Miller",
                nationality = "Australia",
                image = "No Image",
                age = LocalDate.of(1998, Month.AUGUST, 12),
                lastSittingSpot = 0,
                hasSpecialNeeds = false
            ),
            Student(
                id = "7",
                firstName = "George",
                lastName = "Moore",
                nationality = "Japan",
                image = "No Image",
                age = LocalDate.of(1992, Month.OCTOBER, 18),
                lastSittingSpot = 0,
                hasSpecialNeeds = false
            ),
            Student(
                id = "8",
                firstName = "Hannah",
                lastName = "Young",
                nationality = "China",
                image = "No Image",
                age = LocalDate.of(1991, Month.MAY, 30),
                lastSittingSpot = 0,
                hasSpecialNeeds = true
            ),
            Student(
                id = "9",
                firstName = "Isaac",
                lastName = "King",
                nationality = "USA",
                image = "No Image",
                age = LocalDate.of(1990, Month.DECEMBER, 8),
                lastSittingSpot = 0,
                hasSpecialNeeds = false
            ),
            Student(
                id = "10",
                firstName = "Julia",
                lastName = "Lee",
                nationality = "Brazil",
                image = "No Image",
                age = LocalDate.of(1989, Month.JULY, 3),
                lastSittingSpot = 0,
                hasSpecialNeeds = false
            )
        )
    }
}