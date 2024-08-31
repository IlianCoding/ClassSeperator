package com.app.assignmentandroidapplication.ui.screen.startingScreens

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.assignmentandroidapplication.R
import com.app.assignmentandroidapplication.model.Classroom
import com.app.assignmentandroidapplication.model.Desk
import com.app.assignmentandroidapplication.model.Position
import com.app.assignmentandroidapplication.model.configuration.layoutType.LayoutType

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(classrooms: List<Classroom>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Classrooms") }
            )
        },
        bottomBar = {
            BottomButtons()
        }
    ) {
        if (classrooms.isEmpty()){
            Text("No classrooms found")
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                items(classrooms) { classroom ->
                    ClassroomCard(classroom)
                }
            }
        }
    }
}

@Composable
fun BottomButtons() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { /* Handle Import */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Import Classrooms")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { /* Handle Add Classroom */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add a Classroom")
        }
    }
}

@Composable
fun ClassroomCard(classroom: Classroom) {
    var imageId = when (classroom.layoutType) {
        LayoutType.ROW_BY_ROW -> R.drawable.rowbyrow
        LayoutType.U_SHAPE -> R.drawable.ushape
        LayoutType.GROUPED_LAYOUT -> R.drawable.grouped
        LayoutType.LAB_LAYOUT -> R.drawable.laboratory
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle classroom click */ },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = classroom.name, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        classrooms = listOf(
            Classroom("1",
                "Class 1",
                LayoutType.ROW_BY_ROW,
                listOf(
                    Desk("1", Position(1,1), "student1"),
                    Desk("2", Position(1,2), "student2"),
                    Desk("3", Position(1,3), "student3")),
                mutableListOf(
                    "student1",
                    "student2",
                    "student3"
                )
            ),
            Classroom("2",
                "Class 2",
                LayoutType.LAB_LAYOUT,
                listOf(
                    Desk("4", Position(1,1), "student4"),
                    Desk("5", Position(1,2), "student5"),
                    Desk("6", Position(1,3), "student6")),
                mutableListOf(
                    "student4",
                    "student5",
                    "student6"
                )
            ),
            Classroom("3",
                "Class 2",
                LayoutType.U_SHAPE,
                listOf(
                    Desk("4", Position(1,1), "student4"),
                    Desk("5", Position(1,2), "student5"),
                    Desk("6", Position(1,3), "student6")),
                mutableListOf(
                    "student4",
                    "student5",
                    "student6"
                )
            ),
            Classroom("4",
                "Class 2",
                LayoutType.GROUPED_LAYOUT,
                listOf(
                    Desk("4", Position(1,1), "student4"),
                    Desk("5", Position(1,2), "student5"),
                    Desk("6", Position(1,3), "student6")),
                mutableListOf(
                    "student4",
                    "student5",
                    "student6"
                )
            ),
            Classroom("5",
                "Class 2",
                LayoutType.ROW_BY_ROW,
                listOf(
                    Desk("4", Position(1,1), "student4"),
                    Desk("5", Position(1,2), "student5"),
                    Desk("6", Position(1,3), "student6")),
                mutableListOf(
                    "student4",
                    "student5",
                    "student6"
                )
            )
        )
    )
}