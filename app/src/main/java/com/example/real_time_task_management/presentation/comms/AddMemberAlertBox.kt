package com.example.real_time_task_management.presentation.comms

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.real_time_task_management.ui.theme.RealTImeTaskManagementTheme

@Composable
fun AddMemberAlertBox(
    onDismiss: () -> Unit = {},
    onConfirm: (username : String) -> Unit = {},
    projectId: Long,
) {

    var username by remember { mutableStateOf("") }

    AlertDialog(
        text = { TextField(value = username, onValueChange = { username = it },label = { Text(text = "Username") }) },
        title = { Text("Add Member to This Project $projectId ID", fontSize = 20.sp) },
        onDismissRequest = {onDismiss},
        confirmButton = {
            TextButton(onClick = { onConfirm(username) })
            { Text(text = "Add") }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() })
            { Text(text = "Cancel") }
        }
    )
}

@Composable
@Preview
fun AddMemberAlertBoxPreview() {
    RealTImeTaskManagementTheme {
        AddMemberAlertBox(projectId = 1)
    }
}

