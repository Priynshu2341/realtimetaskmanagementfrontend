package com.example.real_time_task_management.domain.repository

import android.content.Context
import androidx.navigation.NavController
import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.real_time_task_management.dto.requestdto.CommentReqDTO
import com.example.real_time_task_management.dto.requestdto.ProjectReqDTO
import com.example.real_time_task_management.dto.requestdto.TaskReqDTO
import com.example.real_time_task_management.dto.responsedto.CommentResponseDTO
import com.example.real_time_task_management.dto.responsedto.ProjectResponseDTO
import com.example.real_time_task_management.dto.responsedto.TaskResponseDTO
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {

    suspend fun findAllProject(): List<ProjectResponseDTO>
    fun getPagedProject(): Flow<PagingData<ProjectResponseDTO>>

    fun getPagedTaskByProjectId(projectId: Long): Flow<PagingData<TaskResponseDTO>>

    suspend fun createProject(project: ProjectReqDTO): ProjectResponseDTO

    suspend fun addMemberToProject(projectId: Long, username: String, context: Context)

    suspend fun deleteProjectById(projectId: Long, context: Context)

    fun getCommentsById(taskId: Long): Flow<PagingData<CommentResponseDTO>>

    suspend fun createTaskById(projectId: Long, task: TaskReqDTO,context: Context,navController: NavController)

    suspend fun addCommentById(taskId: Long, comment: CommentReqDTO,context: Context,navController: NavController)

    suspend fun updateTaskStatus(taskId: Long,context: Context,status:String)

    suspend fun updateTaskPriority(taskId: Long,context: Context,priority:String)
}