package com.example.real_time_task_management.presentation.comms

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import com.example.real_time_task_management.dto.responsedto.ProjectResponseDTO
import com.example.real_time_task_management.navigation.Screens
import com.example.real_time_task_management.presentation.viewmodel.AuthViewModel
import com.example.real_time_task_management.presentation.viewmodel.ServiceViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProjectCard(project: ProjectResponseDTO, onClick: () -> Unit = {},navController: NavController) {
    var showAddMemberDialogBox by remember { mutableStateOf(false) }
    var showDeleteDialogBox by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val viewmodel: ServiceViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    val userRole = authViewModel.userRole

    // 🔹 Delete confirmation box
    if (showDeleteDialogBox) {
        ShowAlertBox(
            onDismiss = { showDeleteDialogBox = false },
            onConfirm = {
                viewmodel.deleteProjectById(project.id, context)
                navController.navigate(Screens.ProjectsScreen.route)
            },
            text = "Are you sure you want to delete this project?",
            title = "Delete Project?"
        )
    }

    // 🔹 Add member dialog
    if (showAddMemberDialogBox) {
        AddMemberAlertBox(
            onDismiss = { showAddMemberDialogBox = false },
            onConfirm = { username ->
                viewmodel.addMemberToProject(project.id, username, context)
                showAddMemberDialogBox = false
            },
            projectId = project.id
        )
    }

    // 🔹 Card UI
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0)),
        onClick = { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // 🔸 Top Row: Project Title (Left) + Admin Buttons (Right)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
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

                if (userRole == "ADMIN") {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showAddMemberDialogBox = true }) {
                            Text(
                                text = "Add Member",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        IconButton(onClick = { showDeleteDialogBox = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Icon",
                                tint = Color.Red,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 🔸 Project Description
            Text(
                text = project.description ?: "",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray,
                lineHeight = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 🔸 Dates Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Created On:",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    viewmodel.formatDate(project.createdAt)?.let {
                        Text(text = it, color = Color.Black, fontSize = 13.sp)
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "End Date:",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    if (project.endDate == null) {
                        Text(
                            text = "To be Updated",
                            color = Color.Black,
                            fontSize = 13.sp
                        )
                    } else {
                        viewmodel.formatDate(project.endDate)?.let {
                            Text(text = it, color = Color.Black, fontSize = 13.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 🔸 Footer Row (CreatedBy + Project ID)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CreatedBy: ${project.createdByUsername}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "#${project.id}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ShowProjectCard() {
    val navController = rememberNavController()

    val projects = ProjectResponseDTO(
        id = 1,
        title = "Project Alpha",
        description = "A sample project for preview purposes.",
        createdAt = "2025-10-09T00:00:00",
        endDate = null,
        createdByUsername = "admin_user"
    )

    ProjectCard(project = projects, onClick = {},navController)
}
