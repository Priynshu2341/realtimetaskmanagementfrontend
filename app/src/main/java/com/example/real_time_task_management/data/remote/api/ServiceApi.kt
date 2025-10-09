package com.example.real_time_task_management.data.remote.api


import com.example.real_time_task_management.dto.requestdto.ProjectReqDTO
import com.example.real_time_task_management.dto.responsedto.CommentResponseDTO
import com.example.real_time_task_management.dto.responsedto.PagedResponse
import com.example.real_time_task_management.dto.responsedto.ProjectResponseDTO
import com.example.real_time_task_management.dto.responsedto.TaskResponseDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET("/project/find/all")
    suspend fun getAllProject(): List<ProjectResponseDTO>


    @GET("/paging/project/all")
    suspend fun getAllProjectPaged(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): PagedResponse<ProjectResponseDTO>

    @GET("/pagingTask/findByProjectIdPaged/{projectId}")
    suspend fun getTasksByProjectIdPaged(
        @Path("projectId") projectId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int,

        ): PagedResponse<TaskResponseDTO>

    @POST("/project/create")
    suspend fun createProject(@Body project: ProjectReqDTO): ProjectResponseDTO

    @POST("/projectMember/add/{projectId}")
    suspend fun addMemberToProject(@Path("projectId") projectId: Long, @Body username: String)

    @DELETE("/project/delete/{id}")
    suspend fun deleteProject(@Path("id") projectId: Long)

    @GET("/paging/comments/find/{id}")
    suspend fun getCommentsByTaskId(
        @Path("id") taskId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): PagedResponse<CommentResponseDTO>
}