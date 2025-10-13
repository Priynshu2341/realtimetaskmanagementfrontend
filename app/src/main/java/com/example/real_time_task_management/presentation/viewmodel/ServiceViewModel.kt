package com.example.real_time_task_management.presentation.viewmodel

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.real_time_task_management.domain.repository.ServiceRepository
import com.example.real_time_task_management.dto.requestdto.CommentReqDTO
import com.example.real_time_task_management.dto.requestdto.ProjectReqDTO
import com.example.real_time_task_management.dto.requestdto.TaskReqDTO
import com.example.real_time_task_management.dto.responsedto.CommentResponseDTO
import com.example.real_time_task_management.dto.responsedto.ProjectResponseDTO
import com.example.real_time_task_management.dto.responsedto.TaskResponseDTO
import com.example.real_time_task_management.navigation.Screens
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

    fun getCommentsPaged(taskId: Long): Flow<PagingData<CommentResponseDTO>> {
        return serviceRepository.getCommentsById(taskId).cachedIn(viewModelScope)
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

    fun createProject(project: ProjectReqDTO, context: Context, navController: NavController) {
        viewModelScope.launch {
            try {
                serviceRepository.createProject(project)
                Log.d("response", "createProject: $project")
                Toast.makeText(context, "Project Created", Toast.LENGTH_SHORT).show()
                navController.navigate(Screens.ProjectsScreen.route)
            } catch (e: Exception) {
                Log.d("error", "createProject: ${e.message}")
                Toast.makeText(context, "Project creation Failed ${e.message}", Toast.LENGTH_SHORT)
                    .show()

            }
        }
    }

    fun addMemberToProject(projectId: Long, username: String, context: Context) {
        viewModelScope.launch {
            serviceRepository.addMemberToProject(projectId, username, context = context)
        }
    }

    fun deleteProjectById(projectId: Long, context: Context) {
        viewModelScope.launch {
            serviceRepository.deleteProjectById(projectId, context = context)
        }
    }

    fun createTaskById(
        projectId: Long,
        task: TaskReqDTO,
        context: Context,
        navController: NavController,
    ) {
        viewModelScope.launch {
            serviceRepository.createTaskById(projectId, task, context = context, navController)
        }
    }

    fun createCommentByID(
        taskId: Long,
        comment: CommentReqDTO,
        context: Context,
        navController: NavController,
    ) {
        viewModelScope.launch {
            serviceRepository.addCommentById(taskId, comment, context, navController)
        }
    }

    fun updateTaskStatus(taskId: Long, status: String, context: Context) {
        viewModelScope.launch {
            serviceRepository.updateTaskStatus(taskId, context, status)
        }
    }

    fun updateTaskPriority(taskId: Long, priority: String, context: Context) {
        viewModelScope.launch {
            serviceRepository.updateTaskPriority(taskId, context, priority)
        }
    }
}