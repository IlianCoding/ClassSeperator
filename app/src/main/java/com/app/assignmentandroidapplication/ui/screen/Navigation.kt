package com.app.assignmentandroidapplication.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
        composable("home") {
            val homeState = remember { mutableStateOf<CentralAppState>(CentralAppState.Loading) }

            when (val state = homeState.value) {
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

        composable(
            "classroom_detail/{classroomId}",
            arguments = listOf(navArgument("classroomId") { type = NavType.IntType })
        ) { backStackEntry ->
            val classroomId = backStackEntry.arguments?.getInt("classroomId") ?: return@composable
            val classroomState = remember { mutableStateOf<ClassroomState>(ClassroomState.LoadingClassroom) }

            when (val state = classroomState.value) {
                is ClassroomState.LoadingClassroom -> LoadingScreen()
                is ClassroomState.LoadedClassroom -> ClassroomDetailScreen(navController, state.classroom, state.students)
                is ClassroomState.ErrorClassroom -> ErrorScreen(errorMessage = state.errorMessage)
            }
        }
    }
}