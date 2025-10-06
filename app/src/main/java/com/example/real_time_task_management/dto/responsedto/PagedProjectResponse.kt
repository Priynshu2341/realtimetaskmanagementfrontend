package com.example.real_time_task_management.dto.responsedto

import com.example.real_time_task_management.domain.model.Projects

data class PagedResponse<T>(
    val content: List<T>,
    val pageable: Pageable,
    val last: Boolean,
    val totalPages: Int,
    val totalElements: Int,
    val size: Int,
    val number: Int,
    val first: Boolean,
    val numberOfElements: Int,
    val empty: Boolean,
)

data class Pageable(
    val pageNumber: Int,
    val pageSize: Int,
    val offset: Long,
    val paged: Boolean,
    val unpaged: Boolean,
)