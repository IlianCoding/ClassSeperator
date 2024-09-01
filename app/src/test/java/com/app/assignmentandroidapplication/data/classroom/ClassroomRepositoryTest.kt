package com.app.assignmentandroidapplication.data.classroom

import android.content.Context
import com.app.assignmentandroidapplication.model.Classroom
import com.app.assignmentandroidapplication.model.Desk
import com.app.assignmentandroidapplication.model.Position
import com.app.assignmentandroidapplication.model.configuration.layoutType.LayoutType
import com.app.assignmentandroidapplication.utils.LogHelper
import com.app.assignmentandroidapplication.utils.gson.GsonWriterReader
import com.google.gson.Gson
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
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import java.io.File

class ClassroomRepositoryTest {
    @get:Rule
    val tempFolder = TemporaryFolder()

    @Mock
    lateinit var mockJsonWriterReader: GsonWriterReader
    @Mock
    lateinit var mockLogHelper: LogHelper

    private lateinit var classroomRepository: ClassroomRepository
    private lateinit var tempFile: File

    private var gson = Gson()

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        tempFile = tempFolder.newFile("classroomSeperator.json")
        println("Temp file path: ${tempFile.absolutePath}")
        classroomRepository = ClassroomRepository(mock(Context::class.java), mockJsonWriterReader, mockLogHelper)
    }

    @Test
    fun testSaveClassroom() {
        val desks = listOf(
            Desk(id = "desk1", position = Position(row = 1, column = 1)),
            Desk(id = "desk2", position = Position(row = 1, column = 2)),
        )
        val studentIds = mutableListOf(
            "student1",
            "student2"
        )
        val classroom = Classroom(
            id = "classroom1",
            name = "Math Class",
            layoutType = LayoutType.ROW_BY_ROW,
            desks = desks,
            studentIds = studentIds
        )

        val existingData = mutableMapOf<String, String>()
        `when`(mockJsonWriterReader.readDataFromFile(tempFile.absolutePath)).thenReturn(existingData)

        classroomRepository.saveClassroom(classroom)
        verify(mockJsonWriterReader).writeDataToFile(anyString(), anyMap())

        val captor = argumentCaptor<Map<String, Any>>()
        verify(mockJsonWriterReader).writeDataToFile(anyString(), captor.capture())
        val savedData = captor.firstValue
        assert(savedData.containsKey("classrooms"))

        val classroomsJson = savedData["classrooms"] as? String
        assertTrue(savedData.containsKey("classrooms"))

        val savedClassrooms = gson.fromJson(classroomsJson, Array<Classroom>::class.java)

        assertEquals(1, savedClassrooms.size)
        assertEquals(classroom.id, savedClassrooms[0].id)
        assertEquals(classroom.name, savedClassrooms[0].name)
        assertEquals(classroom.layoutType, savedClassrooms[0].layoutType)
        assertEquals(classroom.desks[0].id, classroom.desks[0].id)
        assertEquals(classroom.desks[0].position, classroom.desks[0].position)
        assertEquals(classroom.studentIds[0], savedClassrooms[0].studentIds[0])
    }

    @Test
    fun testLoadClassroom() {
        val desks1 = listOf(
            Desk(id = "desk1", position = Position(row = 1, column = 1)),
            Desk(id = "desk2", position = Position(row = 1, column = 2)),
            Desk(id = "desk3", position = Position(row = 2, column = 1)),
            Desk(id = "desk4", position = Position(row = 2, column = 2)),
        )
        val desks2 = listOf(
            Desk(id = "desk1", position = Position(row = 1, column = 1)),
            Desk(id = "desk2", position = Position(row = 1, column = 2)),
            Desk(id = "desk3", position = Position(row = 2, column = 1)),
            Desk(id = "desk4", position = Position(row = 2, column = 2)),
        )
        val studentIds1 = mutableListOf(
            "student1",
            "student2",
            "student3",
            "student4"
        )
        val studentIds2 = mutableListOf(
            "student5",
            "student6",
            "student7",
            "student8"
        )

        val classrooms = listOf(
            Classroom(
                id = "classroom1",
                name = "Math Class",
                layoutType = LayoutType.ROW_BY_ROW,
                desks = desks1,
                studentIds = studentIds1
            ),
            Classroom(
                id = "classroom2",
                name = "Science Class",
                layoutType = LayoutType.LAB_LAYOUT,
                desks = desks2,
                studentIds = studentIds2
            )
        )

        val exisitingData = mutableMapOf("classrooms" to gson.toJson(classrooms))
        `when`(mockJsonWriterReader.readDataFromFile(anyString())).thenReturn(exisitingData)

        val loadedClassroom1 = classroomRepository.loadClassroom("classroom1")
        assertNotNull(loadedClassroom1)
        assertEquals(classrooms[0].id, loadedClassroom1?.id)
        assertEquals(classrooms[0].name, loadedClassroom1?.name)
        assertEquals(classrooms[0].layoutType, loadedClassroom1?.layoutType)
        assertEquals(classrooms[0].desks, loadedClassroom1?.desks)
        assertEquals(classrooms[0].studentIds, loadedClassroom1?.studentIds)

        val loadedClassroom2 = classroomRepository.loadClassroom("classroom2")
        assertNotNull(loadedClassroom2)
        assertEquals(classrooms[1].id, loadedClassroom2?.id)
        assertEquals(classrooms[1].name, loadedClassroom2?.name)
        assertEquals(classrooms[1].layoutType, loadedClassroom2?.layoutType)
        assertEquals(classrooms[1].desks, loadedClassroom2?.desks)
        assertEquals(classrooms[1].studentIds, loadedClassroom2?.studentIds)

        val nonExistingStudent = classroomRepository.loadClassroom("classroom3")
        assertNull(nonExistingStudent)
    }

    @Test
    fun testUpdateClassroom() {
        val desks1 = listOf(
            Desk(id = "desk1", position = Position(row = 1, column = 1)),
            Desk(id = "desk2", position = Position(row = 1, column = 2)),
            Desk(id = "desk3", position = Position(row = 2, column = 1)),
            Desk(id = "desk4", position = Position(row = 2, column = 2)),
        )
        val desks2 = listOf(
            Desk(id = "desk1", position = Position(row = 1, column = 1)),
            Desk(id = "desk2", position = Position(row = 1, column = 2)),
            Desk(id = "desk3", position = Position(row = 2, column = 1)),
            Desk(id = "desk4", position = Position(row = 2, column = 2)),
        )
        val studentIds1 = mutableListOf(
            "student1",
            "student2",
            "student3",
            "student4"
        )
        val studentIds2 = mutableListOf(
            "student5",
            "student6",
            "student7",
            "student8"
        )

        val classrooms = listOf(
            Classroom(
                id = "classroom1",
                name = "Math Class",
                layoutType = LayoutType.ROW_BY_ROW,
                desks = desks1,
                studentIds = studentIds1
            ),
            Classroom(
                id = "classroom2",
                name = "Science Class",
                layoutType = LayoutType.LAB_LAYOUT,
                desks = desks2,
                studentIds = studentIds2
            )
        )

        val exisitingData = mutableMapOf("classrooms" to gson.toJson(classrooms))
        `when`(mockJsonWriterReader.readDataFromFile(anyString())).thenReturn(exisitingData)

        val updatedClassroom = Classroom("classroom1", "Math Class", LayoutType.GROUPED_LAYOUT, desks2, studentIds1)
        classroomRepository.updateClassroom(updatedClassroom)

        val captor = argumentCaptor<Map<String, Any>>()
        verify(mockJsonWriterReader).writeDataToFile(anyString(), captor.capture())
        val savedData = captor.firstValue

        val classroomsJson = savedData["classrooms"] as? String
        assertNotNull(classroomsJson)

        val savedClassrooms = gson.fromJson(classroomsJson, Array<Classroom>::class.java)

        assertEquals(updatedClassroom.id, savedClassrooms[0].id)
        assertEquals(updatedClassroom.name, savedClassrooms[0].name)
        assertEquals(updatedClassroom.layoutType, savedClassrooms[0].layoutType)
        assertEquals(updatedClassroom.desks, savedClassrooms[0].desks)
        assertEquals(updatedClassroom.studentIds, savedClassrooms[0].studentIds)
    }

    @Test
    fun testDeleteClassroom() {
        val desks1 = listOf(
            Desk(id = "desk1", position = Position(row = 1, column = 1)),
            Desk(id = "desk2", position = Position(row = 1, column = 2)),
            Desk(id = "desk3", position = Position(row = 2, column = 1)),
            Desk(id = "desk4", position = Position(row = 2, column = 2)),
        )
        val desks2 = listOf(
            Desk(id = "desk1", position = Position(row = 1, column = 1)),
            Desk(id = "desk2", position = Position(row = 1, column = 2)),
            Desk(id = "desk3", position = Position(row = 2, column = 1)),
            Desk(id = "desk4", position = Position(row = 2, column = 2)),
        )
        val studentIds1 = mutableListOf(
            "student1",
            "student2",
            "student3",
            "student4"
        )
        val studentIds2 = mutableListOf(
            "student5",
            "student6",
            "student7",
            "student8"
        )

        val classrooms = listOf(
            Classroom(
                id = "classroom1",
                name = "Math Class",
                layoutType = LayoutType.ROW_BY_ROW,
                desks = desks1,
                studentIds = studentIds1
            ),
            Classroom(
                id = "classroom2",
                name = "Science Class",
                layoutType = LayoutType.LAB_LAYOUT,
                desks = desks2,
                studentIds = studentIds2
            )
        )

        val exisitingData = mutableMapOf("classrooms" to gson.toJson(classrooms))
        `when`(mockJsonWriterReader.readDataFromFile(anyString())).thenReturn(exisitingData)

        classroomRepository.deleteClassroom("classroom1")

        val captor = argumentCaptor<Map<String, Any>>()
        verify(mockJsonWriterReader).writeDataToFile(anyString(), captor.capture())
        val savedData = captor.firstValue

        val classroomsJson = savedData["classrooms"] as? String
        assertNotNull(classroomsJson)

        val savedClassrooms = gson.fromJson(classroomsJson, Array<Classroom>::class.java)
        assertNull(savedClassrooms.find { it.id == "classroom1" })
    }

    @Test
    fun testGetAllClassrooms() {
        val desks1 = listOf(
            Desk(id = "desk1", position = Position(row = 1, column = 1)),
            Desk(id = "desk2", position = Position(row = 1, column = 2)),
            Desk(id = "desk3", position = Position(row = 2, column = 1)),
            Desk(id = "desk4", position = Position(row = 2, column = 2)),
        )
        val desks2 = listOf(
            Desk(id = "desk1", position = Position(row = 1, column = 1)),
            Desk(id = "desk2", position = Position(row = 1, column = 2)),
            Desk(id = "desk3", position = Position(row = 2, column = 1)),
            Desk(id = "desk4", position = Position(row = 2, column = 2)),
        )
        val studentIds1 = mutableListOf(
            "student1",
            "student2",
            "student3",
            "student4"
        )
        val studentIds2 = mutableListOf(
            "student5",
            "student6",
            "student7",
            "student8"
        )

        val classrooms = listOf(
            Classroom(
                id = "classroom1",
                name = "Math Class",
                layoutType = LayoutType.ROW_BY_ROW,
                desks = desks1,
                studentIds = studentIds1
            ),
            Classroom(
                id = "classroom2",
                name = "Science Class",
                layoutType = LayoutType.LAB_LAYOUT,
                desks = desks2,
                studentIds = studentIds2
            )
        )

        val exisitingData = mutableMapOf("classrooms" to gson.toJson(classrooms))
        `when`(mockJsonWriterReader.readDataFromFile(anyString())).thenReturn(exisitingData)

        val allClassrooms = classroomRepository.loadAllClassrooms()

        assertEquals(2, allClassrooms.size)
        assertEquals("classroom1", allClassrooms[0].id)
        assertEquals("classroom2", allClassrooms[1].id)
    }
}