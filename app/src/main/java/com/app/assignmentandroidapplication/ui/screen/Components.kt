package com.app.assignmentandroidapplication.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.assignmentandroidapplication.R

@Composable
fun BottomButtons() {
    val importIcon = painterResource(id = R.drawable.ic_import)
    val addIcon = painterResource(id = R.drawable.ic_add)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.End
        ) {
            IconButton(
                onClick = { /* Handle Import click */ },
                modifier = Modifier
                    .size(48.dp)
                    .shadow(4.dp, shape = CircleShape, clip = false)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onSurface,
                        shape = CircleShape
                    )
                    .background(MaterialTheme.colorScheme.onSurface, shape = CircleShape)
            ) {
                Icon(
                    painter = importIcon,
                    contentDescription = "Import Classrooms",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            IconButton(
                onClick = { /* Handle Add Classroom click */ },
                modifier = Modifier
                    .size(48.dp)
                    .shadow(4.dp, shape = CircleShape, clip = false)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onSurface,
                        shape = CircleShape
                    )
                    .background(MaterialTheme.colorScheme.onSurface, shape = CircleShape)
            ) {
                Icon(
                    painter = addIcon,
                    contentDescription = "Add a Classroom",
                    tint = Color.White
                )
            }
        }
    }
}