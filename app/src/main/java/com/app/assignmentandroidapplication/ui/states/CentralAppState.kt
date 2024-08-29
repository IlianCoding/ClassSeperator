package com.app.assignmentandroidapplication.ui.states

import com.app.assignmentandroidapplication.model.Classroom

sealed class CentralAppState {
    object Loading : CentralAppState();
    data class Loaded(val classrooms: List<Classroom>) : CentralAppState()
    data class Error(val errorMessage: String) : CentralAppState();
}