package com.example.real_time_task_management.dto.responsedto


data class CommentResponseDTO (
    val id: Long,
    val content : String,
    val username : String,
    val createdAt : String
)