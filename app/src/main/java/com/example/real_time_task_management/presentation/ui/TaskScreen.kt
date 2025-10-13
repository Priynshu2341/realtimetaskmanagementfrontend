package com.example.real_time_task_management.presentation.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Room
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.filter
import com.example.real_time_task_management.dto.responsedto.ProjectResponseDTO
import com.example.real_time_task_management.dto.responsedto.TaskResponseDTO
import com.example.real_time_task_management.navigation.Screens
import com.example.real_time_task_management.presentation.comms.CommentsBottomSheet
import com.example.real_time_task_management.presentation.comms.TaskCard
import com.example.real_time_task_management.presentation.viewmodel.AuthViewModel
import com.example.real_time_task_management.presentation.viewmodel.ServiceViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    navController: NavController,
    viewModel: ServiceViewModel = hiltViewModel(),
    projectId: Long,
) {
    var selectedFilter by remember { mutableStateOf("All") }

    val authViewModel: AuthViewModel = hiltViewModel()
    val userRole = runBlocking { authViewModel.userPrefs.getUser()?.user?.userRole ?: "" }
    val taskPaged = remember(selectedFilter) {
        viewModel.getTaskPaged(projectId).map { pagingData ->
            pagingData.filter { task ->
                when (selectedFilter) {
                    "All" -> true
                    "Done" -> task.status == "DONE"
                    "In Progress" -> task.status == "TODO"
                    else -> false
                }
            }
        }
    }.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All Projects") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }, floatingActionButton = {
            if (userRole == "ADMIN") {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screens.AddTaskScreen.createRoute(id = projectId))
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Task")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf("All", "Done", "In Progress").forEach { filter ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        RadioButton(
                            selected = selectedFilter == filter,
                            onClick = { selectedFilter = filter }
                        )
                        Text(
                            text = filter,
                            fontSize = 14.sp,
                            fontWeight = if (selectedFilter == filter) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(
                    count = taskPaged.itemCount,
                    key = { index -> taskPaged[index]?.id ?: index }
                ) { index ->
                    val task = taskPaged[index]
                    task?.let {
                        TaskCard(task, task.id,navController)
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TaskScreenPreview() {
    val navController = rememberNavController()
    val fakeProjects = listOf(
        TaskResponseDTO(
            id = 1,
            title = "Demo Project 1",
            description = "This is a mock project for preview. It simulates real backend data.",
            status = "2025-10-03T01:25:41",
            project = "2025-10-10T09:12:00",
            assignee = "PreviewUser1",
            dueDate = "2025-10-03T01:25:41",
            priority = "High"
        ),
        TaskResponseDTO(
            id = 1,
            title = "Demo Project 1",
            description = "This is a mock project for preview. It simulates real backend data.",
            status = "2025-10-03T01:25:41",
            project = "2025-10-10T09:12:00",
            assignee = "PreviewUser1",
            dueDate = "2025-10-03T01:25:41",
            priority = "High"
        ),
        TaskResponseDTO(
            id = 1,
            title = "Demo Project 1",
            description = "This is a mock project for preview. It simulates real backend data.",
            status = "2025-10-03T01:25:41",
            project = "2025-10-10T09:12:00",
            assignee = "PreviewUser1",
            dueDate = "2025-10-03T01:25:41",
            priority = "High"
        )
    )
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Preview - All Projects") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            var selectedFilter by remember { mutableStateOf("All") }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf("All", "Done", "In Progress").forEach { filter ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        RadioButton(
                            selected = selectedFilter == filter,
                            onClick = { selectedFilter = filter }
                        )
                        Text(
                            text = filter,
                            fontSize = 14.sp,
                            fontWeight = if (selectedFilter == filter) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(fakeProjects.size) { index ->
                    TaskCard(fakeProjects[index], taskId = 1,navController)
                }
            }
        }
    }
}
