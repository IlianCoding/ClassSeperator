package com.app.assignmentandroidapplication.data.classroom

import android.content.Context
import com.app.assignmentandroidapplication.model.Classroom
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClassroomRepository @Inject constructor(
    private val context: Context,
    private val jsonSerializer: Json
) : IClassroomRepository{

    private val jsonFileName = "assigningClassroom.json"
    private val jsonFile by lazy { File(context.filesDir, jsonFileName) }

    override fun saveClassroom(classroom: Classroom) {
        val classrooms = loadAllClassrooms().toMutableList()
        classrooms.removeAll { it.id == classroom.id }
        classrooms.add(classroom)
        saveAllClassrooms(classrooms)
    }

    override fun loadClassroom(id: String): Classroom? {
        return loadAllClassrooms().find { it.id == id }
    }

    override fun updateClassroom(classroom: Classroom) {
        val allClassrooms = loadAllClassrooms().toMutableList()
        val index = allClassrooms.indexOfFirst { it.id == classroom.id }
        if (index != -1) {
            allClassrooms[index] = classroom
            saveAllClassrooms(allClassrooms)
        }
    }

    override fun deleteClassroom(id: String) {
        val classrooms = loadAllClassrooms().toMutableList()
        classrooms.removeAll { it.id == id }
        saveAllClassrooms(classrooms)
        deleteStudentImages(id)
    }

    override fun loadAllClassrooms(): List<Classroom> {
        return if (jsonFile.exists()) {
            val jsonString = jsonFile.readText()
            jsonSerializer.decodeFromString(jsonString)
        } else {
            emptyList()
        }
    }

    override fun saveAllClassrooms(classrooms: List<Classroom>) {
        val jsonString = jsonSerializer.encodeToString(classrooms)
        jsonFile.writeText(jsonString)
    }

    private fun deleteStudentImages(classroomId: String){
        val imagesDir = File(context.filesDir, "images/$classroomId")
        if (imagesDir.exists()) {
            imagesDir.deleteRecursively()
        }
    }
}