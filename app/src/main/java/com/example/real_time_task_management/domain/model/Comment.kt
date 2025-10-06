package com.example.real_time_task_management.domain.model

import java.time.LocalDateTime

data class Comment(
    val id: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val user: User,
    val task: Tasks
)