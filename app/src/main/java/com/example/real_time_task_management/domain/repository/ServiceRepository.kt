package com.example.real_time_task_management.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.real_time_task_management.dto.responsedto.ProjectResponseDTO
import com.example.real_time_task_management.dto.responsedto.TaskResponseDTO
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {

    suspend fun findAllProject() : List<ProjectResponseDTO>
    fun getPagedProject() : Flow<PagingData<ProjectResponseDTO>>

    fun getPagedTaskByProjectId(projectId: Long) : Flow<PagingData<TaskResponseDTO>>
}