package com.app.assignmentandroidapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.assignmentandroidapplication.service.classroom.IClassroomService
import com.app.assignmentandroidapplication.service.student.IStudentService
import com.app.assignmentandroidapplication.ui.state.ClassroomState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassroomDetailViewModel @Inject constructor(
    private val classroomService: IClassroomService,
    private val studentService: IStudentService
) : ViewModel() {

    private val _state = MutableStateFlow<ClassroomState>(ClassroomState.LoadingClassroom)
    val state: StateFlow<ClassroomState> = _state.asStateFlow()

    fun loadClassroomDetails(classroomId: String) {
        viewModelScope.launch {
            try {
                val (classroom, students) = classroomService.loadClassroomDetails(classroomId)
                if (classroom != null) {
                    _state.value = ClassroomState.LoadedClassroom(classroom, students)
                } else {
                    _state.value = ClassroomState.ErrorClassroom("Classroom not found")
                }
            } catch (e: Exception) {
                _state.value = ClassroomState.ErrorClassroom("Failed to load classroom details: ${e.message}")
            }
        }
    }

    fun refreshClassroomDetails(classroomId: String) {
        loadClassroomDetails(classroomId)
    }
}