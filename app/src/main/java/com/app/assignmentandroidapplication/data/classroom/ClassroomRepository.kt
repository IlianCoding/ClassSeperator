package com.app.assignmentandroidapplication.data.classroom

import android.content.Context
import com.app.assignmentandroidapplication.model.Classroom
import com.app.assignmentandroidapplication.model.Desk
import com.app.assignmentandroidapplication.model.Position
import com.app.assignmentandroidapplication.model.configuration.layoutType.LayoutType
import com.app.assignmentandroidapplication.utils.LogHelper
import com.app.assignmentandroidapplication.utils.gson.GsonWriterReader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ClassroomRepository @Inject constructor(
    private val context: Context,
    private val gsonWriterReader: GsonWriterReader,
    private val logger: LogHelper = LogHelper("ClassroomRepository")
) : IClassroomRepository {
    private val fileName = "classroomSeperator.json"
    private val gson = Gson()

    override fun saveClassroom(classroom: Classroom) {
        logger.d("Saving classroom: ${classroom.name}")

        val data = gsonWriterReader.readDataFromFile(fileName).toMutableMap()
        val classroomsJson = data["classrooms"] as? String
        val classroomsType = object : TypeToken<MutableList<Classroom>>() {}.type
        val classrooms: MutableList<Classroom> = gson.fromJson(classroomsJson, classroomsType) ?: mutableListOf()

        classrooms.add(classroom)
        data["classrooms"] = gson.toJson(classrooms)
        gsonWriterReader.writeDataToFile(fileName, data)
    }

    override fun loadClassroom(id: String): Classroom? {
        logger.d("Loading classroom with id: $id")

        val data = gsonWriterReader.readDataFromFile(fileName)
        val classroomsJson = data["classrooms"] as? String
        val classroomsType = object : TypeToken<List<Classroom>>() {}.type
        val classrooms: List<Classroom> = gson.fromJson(classroomsJson, classroomsType) ?: emptyList()

        return classrooms.find { it.id == id }
    }

    override fun updateClassroom(classroom: Classroom) {
        logger.d("Updating classroom: ${classroom.name}")

        val data = gsonWriterReader.readDataFromFile(fileName).toMutableMap()
        val classroomsJson = data["classrooms"] as? String
        val classroomsType = object : TypeToken<MutableList<Classroom>>() {}.type
        val classrooms: MutableList<Classroom> = gson.fromJson(classroomsJson, classroomsType) ?: mutableListOf()

        val index = classrooms.indexOfFirst { it.id == classroom.id }
        if (index >= 0) {
            classrooms[index] = classroom
            data["classrooms"] = gson.toJson(classrooms)
            gsonWriterReader.writeDataToFile(fileName, data)
        } else {
            logger.w("Classroom with id ${classroom.id} not found for updating!")
        }
    }

    override fun deleteClassroom(id: String) {
        logger.d("Deleting classroom with id: $id")

        val data = gsonWriterReader.readDataFromFile(fileName).toMutableMap()
        val classroomsJson = data["classrooms"] as? String
        val classroomsType = object : TypeToken<MutableList<Classroom>>() {}.type
        val classrooms: MutableList<Classroom> = gson.fromJson(classroomsJson, classroomsType) ?: mutableListOf()

        classrooms.removeAll { it.id == id }
        data["classrooms"] = gson.toJson(classrooms)
        gsonWriterReader.writeDataToFile(fileName, data)
    }

    override fun loadAllClassrooms(): List<Classroom> {
        logger.d("Loading all classrooms")

        val data = gsonWriterReader.readDataFromFile(fileName)
        val classroomsJson = data["classrooms"] as? String
        val classroomsType = object : TypeToken<List<Classroom>>() {}.type

        return gson.fromJson(classroomsJson, classroomsType) ?: emptyList()
    }

    override fun saveAllClassrooms(classrooms: List<Classroom>) {
        logger.d("Saving all classrooms")

        val data = gsonWriterReader.readDataFromFile(fileName).toMutableMap()
        data["classrooms"] = gson.toJson(classrooms)

        gsonWriterReader.writeDataToFile(fileName, data)
    }

    override fun initializeClassrooms() {
        logger.d("Initializing classrooms")

        val classrooms = mutableListOf<Classroom>()

        val classroom1 = Classroom(
            id = UUID.randomUUID().toString(),
            name = "INF101 - A",
            layoutType = LayoutType.ROW_BY_ROW,
            desks = mutableListOf(
                Desk(
                    id = UUID.randomUUID().toString(),
                    position = Position(row = 1, column = 1),
                    assignedStudentId = "student1"
                ),
                Desk(
                    id = UUID.randomUUID().toString(),
                    position = Position(row = 1, column = 2),
                    assignedStudentId = "student2"
                ),
                Desk(
                    id = UUID.randomUUID().toString(),
                    position = Position(row = 1, column = 3),
                    assignedStudentId = "student3"
                ),
                Desk(
                    id = UUID.randomUUID().toString(),
                    position = Position(row = 2, column = 1),
                    assignedStudentId = null
                ),
            ),
            studentIds = mutableListOf(
                "student1",
                "student2",
                "student3"
            )
        )

        val classroom2 = Classroom(
            id = UUID.randomUUID().toString(),
            name = "INF101 - B",
            layoutType = LayoutType.ROW_BY_ROW,
            desks = mutableListOf(
                Desk(
                    id = UUID.randomUUID().toString(),
                    position = Position(row = 1, column = 1),
                    assignedStudentId = "student4"
                ),
                Desk(
                    id = UUID.randomUUID().toString(),
                    position = Position(row = 1, column = 2),
                    assignedStudentId = "student5"
                ),
                Desk(
                    id = UUID.randomUUID().toString(),
                    position = Position(row = 1, column = 3),
                    assignedStudentId = "student6"
                ),
            ),
            studentIds = mutableListOf(
                "student4",
                "student5",
                "student6",
                "student7"
            )
        )

        val classroom3 = Classroom(
            id = UUID.randomUUID().toString(),
            name = "INF203 - A",
            layoutType = LayoutType.ROW_BY_ROW,
            desks = mutableListOf(
                Desk(
                    id = UUID.randomUUID().toString(),
                    position = Position(row = 1, column = 1),
                    assignedStudentId = "student8"
                ),
                Desk(
                    id = UUID.randomUUID().toString(),
                    position = Position(row = 1, column = 2),
                    assignedStudentId = "student9"
                ),
                Desk(
                    id = UUID.randomUUID().toString(),
                    position = Position(row = 1, column = 3),
                    assignedStudentId = "student10"
                )
            ),
            studentIds = mutableListOf(
                "student8",
                "student9",
                "student10",
                "student15",
                "student16",
                "student17",
                "student18"
            )
        )

        val classroom4 = Classroom(
            id = UUID.randomUUID().toString(),
            name = "INF304",
            layoutType = LayoutType.ROW_BY_ROW,
            desks = mutableListOf(
                Desk(
                    id = UUID.randomUUID().toString(),
                    position = Position(row = 1, column = 1),
                    assignedStudentId = "student11"
                ),
                Desk(
                    id = UUID.randomUUID().toString(),
                    position = Position(row = 1, column = 2),
                    assignedStudentId = "student12"
                ),
                Desk(
                    id = UUID.randomUUID().toString(),
                    position = Position(row = 1, column = 3),
                    assignedStudentId = "student13"
                ),
                Desk(
                    id = UUID.randomUUID().toString(),
                    position = Position(row = 2, column = 1),
                    assignedStudentId = "student14"
                ),
                Desk(
                    id = UUID.randomUUID().toString(),
                    position = Position(row = 2, column = 2),
                    assignedStudentId = null
                ),
                Desk(
                    id = UUID.randomUUID().toString(),
                    position = Position(row = 2, column = 3),
                    assignedStudentId = null
                ),
            ),
            studentIds = mutableListOf(
                "student11",
                "student12",
                "student13",
                "student14",
            )
        )

        classrooms.add(classroom1)
        classrooms.add(classroom2)
        classrooms.add(classroom3)
        classrooms.add(classroom4)

        val data = gsonWriterReader.readDataFromFile(fileName).toMutableMap()
        data["classrooms"] = Gson().toJson(classrooms)
        gsonWriterReader.writeDataToFile(fileName, data)
    }
}