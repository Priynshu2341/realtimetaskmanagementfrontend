package com.example.real_time_task_management.data.repositoryimp

import com.example.real_time_task_management.data.remote.api.AuthApi
import com.example.real_time_task_management.domain.model.User
import com.example.real_time_task_management.domain.repository.AuthRepository
import com.example.real_time_task_management.dto.requestdto.LoginReqDTO
import com.example.real_time_task_management.dto.requestdto.RegisterReqDTO
import com.example.real_time_task_management.dto.responsedto.LoginResponseDTO
import com.example.real_time_task_management.dto.responsedto.RegisterResponseDTO
import javax.inject.Inject

class AuthRepositoryImp@Inject constructor(
    private val api: AuthApi
) : AuthRepository {

    override suspend fun register(registerReqDTO: RegisterReqDTO) : RegisterResponseDTO {
        val response = api.register(registerReqDTO)
        return response
    }

    override suspend fun login(loginReqDTO: LoginReqDTO): LoginResponseDTO {
        return api.login(loginReqDTO)
    }
}