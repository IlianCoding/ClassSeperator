package com.app.assignmentandroidapplication.data.student

import android.content.Context
import com.app.assignmentandroidapplication.model.Student
import com.app.assignmentandroidapplication.utils.JsonWriterReader
import com.app.assignmentandroidapplication.utils.LogHelper
import com.app.assignmentandroidapplication.utils.json.LocalDateDeserializer
import com.app.assignmentandroidapplication.utils.json.LocalDateSerializer
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.mockito.ArgumentMatchers.anyMap
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.argumentCaptor
import java.io.File
import java.time.LocalDate

class StudentRepositoryTest {
    @get:Rule
    val tempFolder = TemporaryFolder()

    @Mock
    lateinit var mockJsonWriterReader: JsonWriterReader
    @Mock
    lateinit var mockLogHelper: LogHelper

    private lateinit var studentRepository: StudentRepository
    private lateinit var tempFile: File

    private var gson = GsonBuilder()
        .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())
        .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
        .create()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        tempFile = tempFolder.newFile("classroomSeperator.json")
        println("Temp file path: ${tempFile.absolutePath}")
        studentRepository =StudentRepository(mock(Context::class.java), mockJsonWriterReader, mockLogHelper)
    }

    @Test
    fun testSaveStudent() {

        val student = Student(
            "1",
            "John",
            "Doe",
            "USA",
            "",
            LocalDate.now(),
            false
        )

        val existingData = mutableMapOf<String, String>()
        `when`(mockJsonWriterReader.readFromFile(tempFile.absolutePath)).thenReturn(existingData)

        studentRepository.saveStudent(student)
        verify(mockJsonWriterReader).writeToFile(anyString(), anyMap())

        val captor = argumentCaptor<Map<String, Any>>()
        verify(mockJsonWriterReader).writeToFile(anyString(), captor.capture())
        val savedData = captor.firstValue
        assertTrue(savedData.containsKey("students"))

        val studentsJson = savedData["students"] as? String
        assertNotNull(studentsJson)

        val savedStudents = gson.fromJson<List<Student>>(studentsJson, object : TypeToken<List<Student>>() {}.type)

        assertEquals(1, savedStudents.size)
        assertEquals(student.id, savedStudents[0].id)
        assertEquals(student.firstName, savedStudents[0].firstName)
        assertEquals(student.lastName, savedStudents[0].lastName)
        assertEquals(student.nationality, savedStudents[0].nationality)
        assertEquals(student.birthDate, savedStudents[0].birthDate)
        assertEquals(student.hasSpecialNeeds, savedStudents[0].hasSpecialNeeds)
    }

    @Test
    fun testLoadStudent() {
        val existingStudents =listOf(
            Student("1", "John", "Doe", "USA", "", LocalDate.of(1996, 12, 4), false),
            Student("2", "Jane", "Smith", "Canada", "", LocalDate.of(2001, 6,6), true)
        )

        val existingData = mutableMapOf("students" to gson.toJson(existingStudents))
        `when`(mockJsonWriterReader.readFromFile(anyString())).thenReturn(existingData)

        val loadedStudent1 = studentRepository.loadStudent("1")
        assertNotNull(loadedStudent1)
        assertEquals("1", loadedStudent1?.id)
        assertEquals("John", loadedStudent1?.firstName)
        assertEquals("Doe", loadedStudent1?.lastName)
        assertEquals("USA", loadedStudent1?.nationality)
        assertEquals(LocalDate.of(1996, 12, 4), loadedStudent1?.birthDate)
        assertEquals(false, loadedStudent1?.hasSpecialNeeds)

        val loadedStudent2 = studentRepository.loadStudent("2")
        assertNotNull(loadedStudent2)
        assertEquals("2", loadedStudent2?.id)
        assertEquals("Jane", loadedStudent2?.firstName)
        assertEquals("Smith", loadedStudent2?.lastName)
        assertEquals("Canada", loadedStudent2?.nationality)
        assertEquals(LocalDate.of(2001,6,6), loadedStudent2?.birthDate)
        assertEquals(true, loadedStudent2?.hasSpecialNeeds)

        val nonExistingStudent = studentRepository.loadStudent("3")
        assertNull(nonExistingStudent)
    }

    @Test
    fun testUpdateStudent() {
        val existingStudents =listOf(
            Student("1", "John", "Doe", "USA", "", LocalDate.of(1996, 12, 4), false),
            Student("2", "Jane", "Smith", "Canada", "", LocalDate.of(2001, 6,6), true)
        )

        val existingData = mutableMapOf("students" to gson.toJson(existingStudents))
        `when`(mockJsonWriterReader.readFromFile(anyString())).thenReturn(existingData)

        val updatedStudent = Student("1", "John", "Doe", "UK", "", LocalDate.of(1996, 12, 4), true)
        studentRepository.updateStudent(updatedStudent)

        val captor = argumentCaptor<Map<String, Any>>()
        verify(mockJsonWriterReader).writeToFile(anyString(), captor.capture())
        val savedData = captor.firstValue

        val studentsJson = savedData["students"] as? String
        assertNotNull(studentsJson)

        val savedStudents = gson.fromJson<List<Student>>(studentsJson, object : TypeToken<List<Student>>() {}.type)

        assertEquals(updatedStudent.id, savedStudents[0].id)
        assertEquals(updatedStudent.firstName, savedStudents[0].firstName)
        assertEquals(updatedStudent.lastName, savedStudents[0].lastName)
        assertEquals(updatedStudent.nationality, savedStudents[0].nationality)
        assertEquals(updatedStudent.birthDate, savedStudents[0].birthDate)
        assertEquals(updatedStudent.hasSpecialNeeds, savedStudents[0].hasSpecialNeeds)
    }

    @Test
    fun testDeleteStudent() {
        val existingStudents =listOf(
            Student("1", "John", "Doe", "USA", "", LocalDate.of(1996, 12, 4), false),
            Student("2", "Jane", "Smith", "Canada", "", LocalDate.of(2001, 6,6), true)
        )

        val existingData = mutableMapOf("students" to gson.toJson(existingStudents))
        `when`(mockJsonWriterReader.readFromFile(anyString())).thenReturn(existingData)

        studentRepository.deleteStudent("1")

        val captor = argumentCaptor<Map<String, Any>>()
        verify(mockJsonWriterReader).writeToFile(anyString(), captor.capture())
        val savedData = captor.firstValue

        val savedStudentsJson = savedData["students"] as? String
        assertNotNull(savedStudentsJson)
        val savedStudents = gson.fromJson<List<Student>>(savedStudentsJson, object : TypeToken<List<Student>>() {}.type)
        assertNull(savedStudents.find { it.id == "1" })
    }

    @Test
    fun testGetAllStudents() {
        val existingStudents =listOf(
            Student("1", "John", "Doe", "USA", "", LocalDate.of(1996, 12, 4), false),
            Student("2", "Jane", "Smith", "Canada", "", LocalDate.of(2001, 6,6), true)
        )

        val existingData = mutableMapOf("students" to gson.toJson(existingStudents))
        `when`(mockJsonWriterReader.readFromFile(anyString())).thenReturn(existingData)

        val allStudents = studentRepository.getAllStudents()

        assertEquals(2, allStudents.size)
        assertEquals("1", allStudents[0].id)
        assertEquals("2", allStudents[1].id)
    }
}