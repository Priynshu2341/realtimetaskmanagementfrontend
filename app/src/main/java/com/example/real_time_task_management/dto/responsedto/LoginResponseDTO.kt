package com.example.real_time_task_management.dto.responsedto

data class LoginResponseDTO(
    val accessToken: String,
    val refreshToken: String,
    val user: UserResponse,
)

data class UserResponse(
    val username: String,
    val userRole: String,
)