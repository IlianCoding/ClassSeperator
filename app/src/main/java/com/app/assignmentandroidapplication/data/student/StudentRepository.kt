package com.app.assignmentandroidapplication.data.student

import android.content.Context
import android.graphics.Bitmap
import com.app.assignmentandroidapplication.model.Student
import com.app.assignmentandroidapplication.utils.LogHelper
import com.app.assignmentandroidapplication.utils.gson.GsonWriterReader
import com.app.assignmentandroidapplication.utils.gson.LocalDateDeserializer
import com.app.assignmentandroidapplication.utils.gson.LocalDateSerializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudentRepository @Inject constructor(
    private val context: Context,
    private val gsonWriterReader: GsonWriterReader,
    private val logger: LogHelper = LogHelper("StudentRepository")
) : IStudentRepository {
    private val fileName = "classroomSeperator.json"
    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())
        .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
        .create()

    override fun saveStudent(student: Student) {
        logger.d("Saving student: ${student.id}")

        val data = gsonWriterReader.readDataFromFile(fileName).toMutableMap()
        val studentsJson = data["students"] as? String
        val studentsType = object : TypeToken<MutableList<Student>>() {}.type
        val students: MutableList<Student> = gson.fromJson(studentsJson, studentsType) ?: mutableListOf()

        students.add(student)
        data["students"] = gson.toJson(students)
        gsonWriterReader.writeDataToFile(fileName, data)
    }

    override fun loadStudent(id: String): Student? {
        logger.d("Loading student with id: $id")

        val data = gsonWriterReader.readDataFromFile(fileName)
        val studentsJson = data["students"] as? String
        val studentsType = object : TypeToken<List<Student>>() {}.type
        val students: List<Student> = gson.fromJson(studentsJson, studentsType) ?: emptyList()

        return students.find { it.id == id }
    }

    override fun updateStudent(student: Student) {
        logger.d("Updating student: ${student.id}")

        val data = gsonWriterReader.readDataFromFile(fileName).toMutableMap()
        val studentsJson = data["students"] as? String
        val studentsType = object : TypeToken<MutableList<Student>>() {}.type
        val students: MutableList<Student> = gson.fromJson(studentsJson, studentsType) ?: mutableListOf()

        val index = students.indexOfFirst { it.id == student.id }
        if (index >= 0) {
            students[index] = student
            data["students"] = gson.toJson(students)
            gsonWriterReader.writeDataToFile(fileName, data)
        } else {
            logger.w("Student with id ${student.id} not found for updating!")
        }
    }

    override fun deleteStudent(id: String) {
        logger.d("Deleting student with id: $id")

        val data = gsonWriterReader.readDataFromFile(fileName).toMutableMap()
        val studentsJson = data["students"] as? String
        val studentsType = object : TypeToken<MutableList<Student>>() {}.type
        val students: MutableList<Student> = gson.fromJson(studentsJson, studentsType) ?: mutableListOf()

        students.removeAll { it.id == id }
        data["students"] = gson.toJson(students)
        gsonWriterReader.writeDataToFile(fileName, data)
    }

    override fun getStudentsByIds(studentIds: List<String>): List<Student> {
        logger.d("Loading students with ids: $studentIds")
        val allStudents = getAllStudents()

        return allStudents.filter { it.id in studentIds }
    }

    override fun getAllStudents(): List<Student> {
        logger.d("Loading all students")

        val data = gsonWriterReader.readDataFromFile(fileName)
        val studentsJson = data["students"] as? String
        val studentsType = object : TypeToken<List<Student>>() {}.type

        return gson.fromJson(studentsJson, studentsType) ?: emptyList()
    }

    override fun saveStudentImage(student: Student, image: Bitmap) {
        TODO("Not yet implemented")
    }

    override fun loadStudentImage(student: Student): Bitmap? {
        TODO("Not yet implemented")
    }

    override fun initializeStudents() {
        logger.d("Initializing students")

        val students = mutableListOf<Student>()
        for (i in 1..18) {
            val student = Student(
                id = "student$i",
                firstName = "FirstName$i",
                lastName = "LastName$i",
                nationality = "Nationality$i",
                imageUri = "uri$i",
                birthDate = LocalDate.of(2000, 1, i % 28 + 1),
                hasSpecialNeeds = i % 2 == 0
            )
            students.add(student)
        }

        val data = gsonWriterReader.readDataFromFile(fileName).toMutableMap()
        data["students"] = gson.toJson(students)
        gsonWriterReader.writeDataToFile(fileName, data)
    }
}