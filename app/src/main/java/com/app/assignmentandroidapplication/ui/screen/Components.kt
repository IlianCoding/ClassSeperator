package com.app.assignmentandroidapplication.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomButtons() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            //TODO: Handle Import Classrooms
            onClick = {  },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Import Classrooms")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            //TODO: Handle Add Classroom
            onClick = {  },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add a Classroom")
        }
    }
}