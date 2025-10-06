package com.example.real_time_task_management.dto.responsedto

data class TaskResponseDTO(
    val id: Long,
    val title: String,
    val description: String,
    val priority: String,
    val status: String,
    val dueDate: String,
    val assignee: String,
    val project: String
)

