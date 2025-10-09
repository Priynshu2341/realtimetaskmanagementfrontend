package com.example.real_time_task_management.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.real_time_task_management.data.remote.api.ServiceApi
import com.example.real_time_task_management.dto.responsedto.PagedResponse
import com.example.real_time_task_management.dto.responsedto.ProjectResponseDTO
import javax.inject.Inject
import javax.inject.Named

class ProjectPagingSource (
     private val serviceApi: ServiceApi,
) : PagingSource<Int, ProjectResponseDTO>() {

    override fun getRefreshKey(state: PagingState<Int, ProjectResponseDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProjectResponseDTO> {
        val currentPage = params.key ?: 0
        return try {
            val response: PagedResponse<ProjectResponseDTO> = serviceApi.getAllProjectPaged(
                page = currentPage,
                size = params.loadSize
            )
            val projects: List<ProjectResponseDTO> = response.content
            val nextKey = if (currentPage +1 >= response.totalPages) null else currentPage + 1
            val prevKey = if (currentPage == 0) null else currentPage - 1
            Log.d("PagingSource", "projects class: ${projects.firstOrNull()?.javaClass}")
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