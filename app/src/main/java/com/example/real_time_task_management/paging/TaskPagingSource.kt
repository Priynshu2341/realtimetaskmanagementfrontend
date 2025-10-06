package com.example.real_time_task_management.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.real_time_task_management.data.remote.api.ServiceApi
import com.example.real_time_task_management.dto.responsedto.PagedResponse
import com.example.real_time_task_management.dto.responsedto.TaskResponseDTO
import javax.inject.Inject

class TaskPagingSource @Inject constructor(
    private val api: ServiceApi,
    private val projectId: Long,
) : PagingSource<Int, TaskResponseDTO>() {
    override fun getRefreshKey(state: PagingState<Int, TaskResponseDTO>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)

        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TaskResponseDTO> {
        val currentPage = params.key ?: 0
        val pageSize = params.loadSize
        return try {
            val response: PagedResponse<TaskResponseDTO> = api.getTasksByProjectIdPaged(
                projectId = projectId,
                page = currentPage,
                size = pageSize
            )
            val projects: List<TaskResponseDTO> = response.content
            val prevKey = if (currentPage == 0) null else currentPage - 1
            val nextKey = if (projects.isNotEmpty()) currentPage + 1 else null
            LoadResult.Page(
                data = projects,
                prevKey = prevKey,
                nextKey = nextKey
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


}