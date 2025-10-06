package com.example.real_time_task_management.domain.model

import java.time.LocalDateTime

data class Tasks(
    val id: Long,
    val title: String,
    val description: String,
    val createdAt: LocalDateTime,
    val dueDate: LocalDateTime,
    val assignee: User,
    val status: String,
    val project: Projects,
    val priority: String,


    )