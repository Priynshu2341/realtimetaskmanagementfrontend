package com.example.real_time_task_management.navigation

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.real_time_task_management.presentation.ui.AddProjectScreen
import com.example.real_time_task_management.presentation.ui.AddTaskScreen
import com.example.real_time_task_management.presentation.ui.LoginScreen
import com.example.real_time_task_management.presentation.ui.ProjectScreen
import com.example.real_time_task_management.presentation.ui.SignUpScreen
import com.example.real_time_task_management.presentation.ui.TaskScreen
import com.example.real_time_task_management.presentation.viewmodel.AuthViewModel
import com.example.real_time_task_management.presentation.viewmodel.ServiceViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController: NavHostController) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val serviceViewModel: ServiceViewModel = hiltViewModel()
    val isLoggedIn = authViewModel.isLoggedIn.collectAsState(initial = false)

    val context = LocalContext.current
    LaunchedEffect(isLoggedIn.value) {
        if (isLoggedIn.value) {
            navController.navigate(Screens.ProjectsScreen.route) {
                popUpTo(Screens.LoginScreen.route) {
                    inclusive = true
                }

            }
        } else {
            navController.navigate(Screens.LoginScreen.route) {
                popUpTo(Screens.ProjectsScreen.route) {
                    inclusive = true
                }
            }
        }
    }


    NavHost(navController = navController, startDestination = Screens.LoginScreen.route) {
        composable(Screens.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(Screens.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }

        composable(route = Screens.ProjectsScreen.route) {
            ProjectScreen(
                navController,
                floatingActionButtonClick = { navController.navigate(Screens.AddProjectScreen.route) },
                onLogoutClicked = {
                    authViewModel.logOut(context, navController)
                    Toast.makeText(context, "Logout Successful", Toast.LENGTH_SHORT).show()
                }
            )
        }
        composable(
            route = Screens.TaskScreen.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0
            TaskScreen(navController, projectId = id)
        }
        composable(route = Screens.AddProjectScreen.route) {
            AddProjectScreen(navController, authViewModel.userPrefs)
        }

        composable(
            route = Screens.AddTaskScreen.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0
            AddTaskScreen(navController, authViewModel.userPrefs, id)
        }
    }

}