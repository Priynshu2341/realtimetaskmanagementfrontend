package com.example.real_time_task_management.domain.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.real_time_task_management.dto.requestdto.ProjectReqDTO
import com.example.real_time_task_management.dto.responsedto.CommentResponseDTO
import com.example.real_time_task_management.dto.responsedto.ProjectResponseDTO
import com.example.real_time_task_management.dto.responsedto.TaskResponseDTO
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {

    suspend fun findAllProject() : List<ProjectResponseDTO>
    fun getPagedProject() : Flow<PagingData<ProjectResponseDTO>>

    fun getPagedTaskByProjectId(projectId: Long) : Flow<PagingData<TaskResponseDTO>>

    suspend fun createProject(project: ProjectReqDTO) : ProjectResponseDTO

    suspend fun addMemberToProject(projectId: Long, username: String,context: Context)

    suspend fun deleteProjectById(projectId: Long,context: Context)

     fun getCommentsById(taskId: Long) : Flow<PagingData<CommentResponseDTO>>
}