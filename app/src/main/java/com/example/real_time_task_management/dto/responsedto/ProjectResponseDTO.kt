package com.example.real_time_task_management.dto.responsedto

import java.time.LocalDateTime

data class ProjectResponseDTO(
    val id: Long,
    val title: String,
    val description: String?,
    val createdAt: String?,
    val endDate: String?,
    val createdByUsername: String
)
