package com.app.assignmentandroidapplication.ui.screen.startingScreens

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.assignmentandroidapplication.ui.screen.BottomButtons

@Composable
fun ShimmerEffect(modifier: Modifier = Modifier) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.9f),
        Color.LightGray.copy(alpha = 0.3f),
        Color.LightGray.copy(alpha = 0.9f)
    )

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1000, easing = LinearEasing)
        ), label = ""
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim, 0f),
        end = Offset(translateAnim + 1000f, 0f)
    )

    Box(
        modifier = modifier
            .background(brush = brush)
    )
}

@Composable
fun ShimmerCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        ShimmerEffect(
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoadingScreen() {
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
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            items(6) {
                ShimmerCard()
            }
        }
    }
}


@Preview
@Composable
fun LoadingScreenPreview(){
    LoadingScreen()
}