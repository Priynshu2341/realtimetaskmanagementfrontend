package com.example.real_time_task_management.presentation.comms

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Room
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.real_time_task_management.dto.responsedto.TaskResponseDTO
import com.example.real_time_task_management.presentation.viewmodel.AuthViewModel
import com.example.real_time_task_management.presentation.viewmodel.ServiceViewModel
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCard(
    project: TaskResponseDTO,
    taskId: Long,
    navController: NavController,
) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val viewModel: ServiceViewModel = hiltViewModel()
    val bottomAppBarState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current

    var showCommentSheet by remember { mutableStateOf(false) }
    var statusExpanded by remember { mutableStateOf(false) }
    var priorityExpanded by remember { mutableStateOf(false) }
    val userRole = runBlocking { authViewModel.userPrefs.getUser()?.user?.userRole ?: "" }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Room,
                    contentDescription = "Project Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = project.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = project.description,
                fontSize = 16.sp,
                color = Color.DarkGray,
                lineHeight = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Priority:",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Text(
                        text = project.priority,
                        color = Color.Black,
                        fontSize = 13.sp
                    )
                    if (userRole == "ADMIN") {
                        Box {
                            TextButton(onClick = { priorityExpanded = !priorityExpanded }) {
                                Text(text = "Change Priority")
                            }

                            DropdownMenu(
                                expanded = priorityExpanded,
                                onDismissRequest = { priorityExpanded = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("LOW") },
                                    onClick = {
                                        viewModel.updateTaskPriority(taskId, "LOW", context)
                                        priorityExpanded = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("MEDIUM") },
                                    onClick = {
                                        viewModel.updateTaskPriority(taskId, "MEDIUM", context)
                                        priorityExpanded = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("HIGH") },
                                    onClick = {
                                        viewModel.updateTaskPriority(taskId, "HIGH", context)
                                        priorityExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Status:",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Text(
                        text = project.status,
                        color = Color.Black,
                        fontSize = 13.sp
                    )

                    if (userRole == "ADMIN") {
                        Box {
                            TextButton(onClick = { statusExpanded = !statusExpanded }) {
                                Text(text = "Change Status")
                            }

                            DropdownMenu(
                                expanded = statusExpanded,
                                onDismissRequest = { statusExpanded = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("DONE") },
                                    onClick = {
                                        viewModel.updateTaskStatus(taskId, "DONE", context)
                                        statusExpanded = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("TODO") },
                                    onClick = {
                                        viewModel.updateTaskStatus(taskId, "TODO", context)
                                        statusExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Created By: ${project.assignee}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                    IconButton(onClick = { showCommentSheet = true }) {
                        Icon(Icons.Default.Chat, contentDescription = "Comments")
                    }
                }

                Text(
                    text = "#${project.id}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )
            }
            if (showCommentSheet) {
                CommentsBottomSheet(
                    state = bottomAppBarState,
                    onDismiss = { showCommentSheet = false },
                    taskId = taskId,
                    navController = navController
                )
            }
        }
    }
}
