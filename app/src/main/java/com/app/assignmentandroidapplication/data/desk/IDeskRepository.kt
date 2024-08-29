package com.app.assignmentandroidapplication.data.desk

import com.app.assignmentandroidapplication.model.Desk

interface IDeskRepository {
    fun saveDesk(desk: Desk)
    fun loadDesk(id: String): Desk?
    fun updateDesk(desk: Desk)
    fun deleteDesk(id: String)
    fun getAllDesks(): List<Desk>
}