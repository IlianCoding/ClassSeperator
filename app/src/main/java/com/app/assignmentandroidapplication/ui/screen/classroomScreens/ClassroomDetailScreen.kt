package com.app.assignmentandroidapplication.ui.screen.classroomScreens

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.app.assignmentandroidapplication.model.Classroom
import com.app.assignmentandroidapplication.model.Student
import com.app.assignmentandroidapplication.ui.screen.BottomButtons

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ClassroomDetailScreen(
    classroom: Classroom,
    students: List<Student>
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(classroom.name) }
            )
        },
        bottomBar = {
            BottomButtons()
        }
    ) {

    }
}