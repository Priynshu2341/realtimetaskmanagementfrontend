package com.example.real_time_task_management.domain.model

import java.time.LocalDateTime

data class Projects(
    val id: Long,
    val title: String,
    val description : String,
    val createdAt : LocalDateTime,
    val endDate : LocalDateTime,
    val user: User,
    val tasks: List<Tasks>

)