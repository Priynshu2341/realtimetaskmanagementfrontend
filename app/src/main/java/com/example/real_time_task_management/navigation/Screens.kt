package com.example.real_time_task_management.navigation

import android.net.Uri
import okhttp3.Route

sealed class Screens(val route: String) {
    object LoginScreen : Screens("login_screen")
    object SignUpScreen : Screens("signup_screen")
    object ProjectsScreen : Screens("home_screen")
    object TaskScreen : Screens("task_screen/{id}") {
        fun createRoute(id: Long) = "task_screen/$id"
    }

    object AddProjectScreen : Screens("add_project_screen")


}