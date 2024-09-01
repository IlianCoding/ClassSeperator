package com.app.assignmentandroidapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.app.assignmentandroidapplication.ui.screen.AppNavHost
import com.app.assignmentandroidapplication.ui.theme.AssignmentAndroidApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AssignmentAndroidApplicationTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}