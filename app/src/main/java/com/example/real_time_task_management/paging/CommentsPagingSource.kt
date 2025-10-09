package com.example.real_time_task_management.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.real_time_task_management.data.remote.api.ServiceApi
import com.example.real_time_task_management.dto.responsedto.CommentResponseDTO
import javax.inject.Inject
import javax.inject.Named

class CommentsPagingSource @Inject constructor(
    private val serviceApi: ServiceApi,
    private val taskId: Long,
) : PagingSource<Int, CommentResponseDTO>() {
    override fun getRefreshKey(state: PagingState<Int, CommentResponseDTO>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommentResponseDTO> {
        val currentPage = params.key ?: 0
        val pageSize = params.loadSize
        return try {
            val response = serviceApi.getCommentsByTaskId(taskId,currentPage, size = pageSize)
            val comment = response.content
            val prevKey = if (currentPage == 0) null else currentPage - 1
            val nextKey = if (currentPage +1 >= response.totalPages) null else currentPage + 1
            LoadResult.Page(
                data = comment,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }
}