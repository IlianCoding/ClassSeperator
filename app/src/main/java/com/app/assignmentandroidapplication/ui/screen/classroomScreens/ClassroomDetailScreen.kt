package com.app.assignmentandroidapplication.ui.screen.classroomScreens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.assignmentandroidapplication.R
import com.app.assignmentandroidapplication.model.Classroom
import com.app.assignmentandroidapplication.model.Student
import com.app.assignmentandroidapplication.model.configuration.layoutType.LayoutType
import com.app.assignmentandroidapplication.ui.screen.BottomButtons

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ClassroomDetailScreen(
    navController: NavController,
    classroom: Classroom,
    students: List<Student>
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(classroom.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomButtons()
        }
    ) {
        ClassroomDescription(classroom)
        AssignedStudents(students)
    }
}

@Composable
fun ClassroomDescription(classroom: Classroom) {
    val layout = when(classroom.layoutType){
        LayoutType.ROW_BY_ROW -> "Row by Row layout"
        LayoutType.U_SHAPE -> "U-Shape layout"
        LayoutType.GROUPED_LAYOUT -> "Grouped desks layout"
        LayoutType.LAB_LAYOUT -> "Laboratory desk layout"
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = classroom.name,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = layout,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Number of Desks: ${classroom.desks.size}",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun AssignedStudents(students: List<Student>){
    if (students.isEmpty()){
        Text(
            text = "No students assigned to this classroom!",
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            style = MaterialTheme.typography.labelSmall
        )
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(students.size){ studentIndex ->
                StudentCard(students[studentIndex])
            }
        }
    }
}

@Composable
fun StudentCard(student: Student) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle student click */ },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "Image of student ${student.fullName}",
                modifier = Modifier.size(24.dp)
            )

            Spacer( modifier = Modifier.width(16.dp))

            Text(
                text = student.fullName,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}