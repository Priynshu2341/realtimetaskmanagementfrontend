package com.example.real_time_task_management.data.repositoryimp

import android.content.Context
import android.net.http.HttpException
import android.widget.Toast
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.real_time_task_management.data.remote.api.ServiceApi

import com.example.real_time_task_management.domain.repository.ServiceRepository
import com.example.real_time_task_management.dto.requestdto.ProjectReqDTO
import com.example.real_time_task_management.dto.responsedto.CommentResponseDTO

import com.example.real_time_task_management.dto.responsedto.ProjectResponseDTO
import com.example.real_time_task_management.dto.responsedto.TaskResponseDTO
import com.example.real_time_task_management.paging.CommentsPagingSource
import com.example.real_time_task_management.paging.ProjectPagingSource
import com.example.real_time_task_management.paging.TaskPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class ServiceRepositoryImp @Inject constructor(
    @Named("ServiceApi") private val serviceApi: ServiceApi,
) : ServiceRepository {


    override suspend fun findAllProject(): List<ProjectResponseDTO> {
        return serviceApi.getAllProject()
    }

    override fun getPagedProject(): Flow<PagingData<ProjectResponseDTO>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ), pagingSourceFactory = { ProjectPagingSource(serviceApi) }
        ).flow
    }


    override fun getPagedTaskByProjectId(projectId: Long): Flow<PagingData<TaskResponseDTO>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { TaskPagingSource(serviceApi, projectId) }
        ).flow
    }

    override suspend fun createProject(project: ProjectReqDTO): ProjectResponseDTO {
        return serviceApi.createProject(project)
    }

    override suspend fun addMemberToProject(projectId: Long, username: String, context: Context) {
        try {
            serviceApi.addMemberToProject(projectId, username)
            Toast.makeText(context, "Member added successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Member Already Exist", Toast.LENGTH_SHORT).show()
        }
    }

    override suspend fun deleteProjectById(projectId: Long, context: Context) {
        try {
            serviceApi.deleteProject(projectId)
            Toast.makeText(context, "Project deleted successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to delete project", Toast.LENGTH_SHORT).show()
        }
    }

    override  fun getCommentsById(taskId: Long): Flow<PagingData<CommentResponseDTO>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { CommentsPagingSource(serviceApi, taskId) }
        ).flow

    }


}