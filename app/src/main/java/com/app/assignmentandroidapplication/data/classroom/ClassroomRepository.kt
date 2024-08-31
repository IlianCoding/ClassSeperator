package com.app.assignmentandroidapplication.data.classroom

import android.content.Context
import com.app.assignmentandroidapplication.model.Classroom
import com.app.assignmentandroidapplication.utils.JsonWriterReader
import com.app.assignmentandroidapplication.utils.LogHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ClassroomRepository @Inject constructor(
    private val context: Context,
    private val jsonWriterReader: JsonWriterReader,
    private val logger: LogHelper = LogHelper("ClassroomRepository")
) : IClassroomRepository {
    private val fileName = "classroomSeperator.json"
    private val gson = Gson()

    override fun saveClassroom(classroom: Classroom) {
        logger.d("Saving classroom: ${classroom.name}")

        val data = jsonWriterReader.readFromFile(fileName).toMutableMap()
        val classroomsJson = data["classrooms"] as? String
        val classroomsType = object : TypeToken<MutableList<Classroom>>() {}.type
        val classrooms: MutableList<Classroom> = gson.fromJson(classroomsJson, classroomsType) ?: mutableListOf()

        classrooms.add(classroom)
        data["classrooms"] = gson.toJson(classrooms)
        jsonWriterReader.writeToFile(fileName, data)
    }

    override fun loadClassroom(id: String): Classroom? {
        logger.d("Loading classroom with id: $id")

        val data = jsonWriterReader.readFromFile(fileName)
        val classroomsJson = data["classrooms"] as? String
        val classroomsType = object : TypeToken<List<Classroom>>() {}.type
        val classrooms: List<Classroom> = gson.fromJson(classroomsJson, classroomsType) ?: emptyList()

        return classrooms.find { it.id == id }
    }

    override fun updateClassroom(classroom: Classroom) {
        logger.d("Updating classroom: ${classroom.name}")

        val data = jsonWriterReader.readFromFile(fileName).toMutableMap()
        val classroomsJson = data["classrooms"] as? String
        val classroomsType = object : TypeToken<MutableList<Classroom>>() {}.type
        val classrooms: MutableList<Classroom> = gson.fromJson(classroomsJson, classroomsType) ?: mutableListOf()

        val index = classrooms.indexOfFirst { it.id == classroom.id }
        if (index >= 0) {
            classrooms[index] = classroom
            data["classrooms"] = gson.toJson(classrooms)
            jsonWriterReader.writeToFile(fileName, data)
        } else {
            logger.w("Classroom with id ${classroom.id} not found for updating!")
        }
    }

    override fun deleteClassroom(id: String) {
        logger.d("Deleting classroom with id: $id")

        val data = jsonWriterReader.readFromFile(fileName).toMutableMap()
        val classroomsJson = data["classrooms"] as? String
        val classroomsType = object : TypeToken<MutableList<Classroom>>() {}.type
        val classrooms: MutableList<Classroom> = gson.fromJson(classroomsJson, classroomsType) ?: mutableListOf()

        classrooms.removeAll { it.id == id }
        data["classrooms"] = gson.toJson(classrooms)
        jsonWriterReader.writeToFile(fileName, data)
    }

    override fun loadAllClassrooms(): List<Classroom> {
        logger.d("Loading all classrooms")

        val data = jsonWriterReader.readFromFile(fileName)
        val classroomsJson = data["classrooms"] as? String
        val classroomsType = object : TypeToken<List<Classroom>>() {}.type

        return gson.fromJson(classroomsJson, classroomsType) ?: emptyList()
    }

    override fun saveAllClassrooms(classrooms: List<Classroom>) {
        logger.d("Saving all classrooms")

        val data = jsonWriterReader.readFromFile(fileName).toMutableMap()
        data["classrooms"] = gson.toJson(classrooms)

        jsonWriterReader.writeToFile(fileName, data)
    }
}