package com.example.real_time_task_management.presentation.ui

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Room
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.rememberNavController
import com.example.real_time_task_management.dto.responsedto.ProjectResponseDTO
import com.example.real_time_task_management.presentation.comms.ProjectCard
import com.example.real_time_task_management.presentation.viewmodel.AuthViewModel
import com.example.real_time_task_management.presentation.viewmodel.ServiceViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectScreen(navController: NavController) {
    val viewModel: ServiceViewModel = hiltViewModel()
    val projectsPaged = viewModel.projectsPaged.collectAsLazyPagingItems()
    val authViewModel: AuthViewModel = hiltViewModel()
    val context = LocalContext.current
    var showDialogBox by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All Projects") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back */ }) {
                        Icon(Icons.Default.Room, contentDescription = "Back")
                    }

                }, actions = {
                    IconButton(onClick = {
                     showDialogBox = true
                    }) {
                        Icon(Icons.Default.Lock, contentDescription = "Logout")
                    }
                }
            )
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
                    ProjectCard(project, navController)
                }
            }
        }
    }
    if (showDialogBox) {
        ShowAlertBox(title = "Logout", text = "Are you sure you want to logout?", onConfirm = {
            authViewModel.logOut(context, navController)
            Toast.makeText(context, "Logout Successful", Toast.LENGTH_SHORT).show()
        }, onDismiss = {
            showDialogBox = false
        })
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ProjectCardPreview() {
    val navController = rememberNavController()
    val project = ProjectResponseDTO(
        id = 1,
        title = "First Project",
        createdAt = "213123",
        createdByUsername = "Venom",
        description = "THis is the first Project Created By Venom on 1st January 2023 It is one of the First Project",
        endDate = "1231231231"
    )
    ProjectCard(project = project, navController = navController)
}

@Composable
fun ShowAlertBox(
    text: String,
    title: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        title = { Text(title) },
        text = { Text(text) },
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}
