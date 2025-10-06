package com.example.real_time_task_management.domain.model

data class User(
    val id: Long,
    val username: String,
    val email: String,
    val password: String,
    val refreshToken: String,
    val roleType: String,
)