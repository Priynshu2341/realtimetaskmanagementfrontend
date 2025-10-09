package com.example.real_time_task_management.presentation.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

import com.example.real_time_task_management.navigation.Screens
import com.example.real_time_task_management.presentation.comms.AddMemberAlertBox
import com.example.real_time_task_management.presentation.comms.ProjectCard
import com.example.real_time_task_management.presentation.comms.ShowAlertBox
import com.example.real_time_task_management.presentation.viewmodel.AuthViewModel
import com.example.real_time_task_management.presentation.viewmodel.ServiceViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectScreen(
    navController: NavController,
    floatingActionButtonClick: () -> Unit,
    onLogoutClicked: () -> Unit,
) {
    val viewModel: ServiceViewModel = hiltViewModel()
    val projectsPaged = viewModel.projectsPaged.collectAsLazyPagingItems()
    val authViewModel: AuthViewModel = hiltViewModel()
    val context = LocalContext.current
    var showLogoutConfirmation by remember { mutableStateOf(false) }


    val userRole = authViewModel.userRole

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All Projects") },
                actions = {
                    IconButton(onClick = {
                        showLogoutConfirmation = true
                    }) {
                        Icon(Icons.Default.Lock, contentDescription = "Logout")
                    }
                }
            )
        }, floatingActionButton = {
            if (userRole == "ADMIN") {
                FloatingActionButton(onClick = {
                    floatingActionButtonClick()
                }) {
                    Icon(Icons.Default.Add, contentDescription = "Add Project")
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(
                count = projectsPaged.itemCount,
                key = { index -> projectsPaged[index]?.id ?: index }
            ) { index ->
                projectsPaged[index]?.let { project ->
                    ProjectCard(
                        project,
                        onClick = { navController.navigate(Screens.TaskScreen.createRoute(project.id)) },navController)
                }
            }
        }
    }
    if (showLogoutConfirmation) {
        ShowAlertBox(title = "Logout", text = "Are you sure you want to logout?", onConfirm = {
            onLogoutClicked()
        }, onDismiss = {
            showLogoutConfirmation = false
        })
    }

}


