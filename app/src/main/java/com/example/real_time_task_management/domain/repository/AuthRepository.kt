package com.example.real_time_task_management.domain.repository

import com.example.real_time_task_management.domain.model.User
import com.example.real_time_task_management.dto.requestdto.LoginReqDTO
import com.example.real_time_task_management.dto.requestdto.RefreshReqDTO
import com.example.real_time_task_management.dto.requestdto.RegisterReqDTO
import com.example.real_time_task_management.dto.responsedto.LoginResponseDTO
import com.example.real_time_task_management.dto.responsedto.RegisterResponseDTO

interface AuthRepository {


    suspend fun register(registerReqDTO: RegisterReqDTO) : RegisterResponseDTO

    suspend fun login(loginReqDTO: LoginReqDTO) : LoginResponseDTO

    suspend fun refreshToken(refreshReqDTO: RefreshReqDTO) : LoginResponseDTO
}