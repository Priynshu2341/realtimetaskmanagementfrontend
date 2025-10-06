package com.example.real_time_task_management.data.remote.interceptor

import com.example.real_time_task_management.data.remote.api.AuthApi
import com.example.real_time_task_management.domain.datastore.UserPrefs
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val userPrefs: UserPrefs,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { userPrefs.getAccessToken() }
        val request = chain.request().newBuilder().apply {
            if (!token.isNullOrEmpty()) {
                addHeader("Authorization", "Bearer $token")
            }
        }.build()
        return chain.proceed(request)
    }


}