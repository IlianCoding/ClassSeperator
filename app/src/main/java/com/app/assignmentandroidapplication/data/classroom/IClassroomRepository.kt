package com.app.assignmentandroidapplication.data.classroom

import com.app.assignmentandroidapplication.model.Classroom

interface IClassroomRepository{
    fun saveClassroom(classroom: Classroom)
    fun loadClassroom(id: String): Classroom?
    fun updateClassroom(classroom: Classroom)
    fun deleteClassroom(id: String)
    fun loadAllClassrooms(): List<Classroom>
    fun saveAllClassrooms(classrooms: List<Classroom>)
    fun initializeClassrooms()
}