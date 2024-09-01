package com.app.assignmentandroidapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.assignmentandroidapplication.service.classroom.IClassroomService
import com.app.assignmentandroidapplication.ui.state.CentralAppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val classroomService: IClassroomService
) : ViewModel() {
    private val _state = MutableStateFlow<CentralAppState>(CentralAppState.Loading)
    val state: StateFlow<CentralAppState> = _state.asStateFlow()

    init {
        initializeData()
        loadClassrooms()
    }

    private fun initializeData(){
        viewModelScope.launch {
            classroomService.initializeData()
        }
    }

    private fun loadClassrooms() {
        viewModelScope.launch {
            try {
                val classrooms = classroomService.loadAllClassrooms()
                if (classrooms.isEmpty()) {
                    _state.value = CentralAppState.Error("No classrooms were found")
                } else {
                    _state.value = CentralAppState.Loaded(classrooms)
                }
            } catch (e: Exception) {
                _state.value = CentralAppState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun refreshClassrooms() {
        loadClassrooms()
    }
}