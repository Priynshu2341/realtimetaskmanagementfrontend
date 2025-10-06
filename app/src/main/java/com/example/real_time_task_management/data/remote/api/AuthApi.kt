package com.example.real_time_task_management.data.remote.api

import com.example.real_time_task_management.domain.model.User
import com.example.real_time_task_management.dto.requestdto.LoginReqDTO
import com.example.real_time_task_management.dto.requestdto.RegisterReqDTO
import com.example.real_time_task_management.dto.responsedto.LoginResponseDTO
import com.example.real_time_task_management.dto.responsedto.RegisterResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterReqDTO,
    ) : RegisterResponseDTO


    @POST("auth/login")
    suspend fun login(
        @Body loginReqDTO: LoginReqDTO,
    ): LoginResponseDTO


}