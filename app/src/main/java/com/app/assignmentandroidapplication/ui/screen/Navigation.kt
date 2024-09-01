package com.app.assignmentandroidapplication.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.app.assignmentandroidapplication.ui.screen.classroomScreens.ClassroomDetailScreen
import com.app.assignmentandroidapplication.ui.screen.startingScreens.HomeScreen
import com.app.assignmentandroidapplication.ui.screen.startingScreens.LoadingScreen
import com.app.assignmentandroidapplication.ui.state.CentralAppState
import com.app.assignmentandroidapplication.ui.state.ClassroomState
import com.app.assignmentandroidapplication.ui.viewmodel.ClassroomDetailViewModel
import com.app.assignmentandroidapplication.ui.viewmodel.HomeViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = "home"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // Home Screen
        composable("home") {
            // Access the HomeViewModel
            val homeViewModel: HomeViewModel = hiltViewModel()

            // Observe the state from HomeViewModel
            val homeState by homeViewModel.state.collectAsState()

            // Handle different states in the UI
            when (val state = homeState) {
                is CentralAppState.Loading -> LoadingScreen()
                is CentralAppState.Loaded -> HomeScreen(
                    classrooms = state.classrooms,
                    onClassroomClick = { classroom ->
                        navController.navigate("classroom_detail/${classroom.id}")
                    }
                )
                is CentralAppState.Error -> ErrorScreen(errorMessage = state.errorMessage)
            }
        }

        // Classroom Detail Screen
        composable(
            "classroom_detail/{classroomId}",
            arguments = listOf(navArgument("classroomId") { type = NavType.StringType }) // Updated to StringType to match classroomId type
        ) { backStackEntry ->
            // Access the ClassroomDetailViewModel
            val classroomDetailViewModel: ClassroomDetailViewModel = hiltViewModel()

            // Get the classroomId from the arguments
            val classroomId = backStackEntry.arguments?.getString("classroomId") ?: return@composable

            // Trigger loading of classroom details
            LaunchedEffect(classroomId) {
                classroomDetailViewModel.loadClassroomDetails(classroomId)
            }

            // Observe the state from ClassroomDetailViewModel
            val classroomState by classroomDetailViewModel.state.collectAsState()

            // Handle different states in the UI
            when (val state = classroomState) {
                is ClassroomState.LoadingClassroom -> LoadingScreen()
                is ClassroomState.LoadedClassroom -> ClassroomDetailScreen(navController, state.classroom, state.students)
                is ClassroomState.ErrorClassroom -> ErrorScreen(errorMessage = state.errorMessage)
            }
        }
    }
}