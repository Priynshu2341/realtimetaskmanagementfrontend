package com.example.real_time_task_management.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.real_time_task_management.domain.repository.ServiceRepository
import com.example.real_time_task_management.dto.responsedto.ProjectResponseDTO
import com.example.real_time_task_management.dto.responsedto.TaskResponseDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
    private val serviceRepository: ServiceRepository,
) : ViewModel() {

    private val _projects = MutableStateFlow<List<ProjectResponseDTO>>(emptyList())
    val projects: StateFlow<List<ProjectResponseDTO>> = _projects

    fun getAllProjects() {
        viewModelScope.launch {
            try {
                val response = serviceRepository.findAllProject()
                _projects.value = response
                Log.d("response", "getAllProjects: $response")
            } catch (e: Exception) {
                Log.d("error", "getAllProjects: ${e.message}")
                _projects.value = emptyList()
            }
        }
    }
    val projectsPaged: Flow<PagingData<ProjectResponseDTO>> =
        serviceRepository.getPagedProject().cachedIn(viewModelScope)

    fun getTaskPaged(projectId: Long): Flow<PagingData<TaskResponseDTO>> {
        return serviceRepository.getPagedTaskByProjectId(projectId).cachedIn(viewModelScope)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(dateString: String?): String? {
        return try {
            val parser = DateTimeFormatter.ISO_DATE_TIME
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy") // desired format
            val date = LocalDateTime.parse(dateString, parser)
            date.format(formatter)
        } catch (e: Exception) {
            dateString // fallback if parsing fails
        }
    }


}