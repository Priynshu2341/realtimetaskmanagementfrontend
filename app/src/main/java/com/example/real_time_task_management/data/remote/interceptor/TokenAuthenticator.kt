package com.example.real_time_task_management.data.remote.interceptor

import com.example.real_time_task_management.data.remote.api.AuthApi
import com.example.real_time_task_management.domain.datastore.UserPrefs
import com.example.real_time_task_management.domain.repository.AuthRepository
import com.example.real_time_task_management.dto.requestdto.RefreshReqDTO
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Named

class TokenAuthenticator @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPrefs: UserPrefs,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {

        if (responseCount(response) >= 2) return null

        val refreshToken = runBlocking { userPrefs.getRefreshToken() }

        val newAccessToken = runBlocking {
            try {
                val refreshResponse = authRepository.refreshToken(RefreshReqDTO(refreshToken))
                val token = refreshResponse.accessToken
                if (token.isNotEmpty()) {
                    userPrefs.saveAccessToken(token)
                }
                token
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        } ?: return null

        return response.request.newBuilder()
            .header("Authorization", "Bearer $newAccessToken")
            .build()


    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var temp = response.priorResponse
        while (temp != null) {
            count++
            temp = temp.priorResponse
        }
        return count

    }
}