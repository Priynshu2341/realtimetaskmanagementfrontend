package com.example.real_time_task_management.data.repositoryimp

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.real_time_task_management.data.remote.api.ServiceApi

import com.example.real_time_task_management.domain.repository.ServiceRepository

import com.example.real_time_task_management.dto.responsedto.ProjectResponseDTO
import com.example.real_time_task_management.dto.responsedto.TaskResponseDTO
import com.example.real_time_task_management.paging.ProjectPagingSource
import com.example.real_time_task_management.paging.TaskPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ServiceRepositoryImp @Inject constructor(
    private val serviceApi: ServiceApi,
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


}