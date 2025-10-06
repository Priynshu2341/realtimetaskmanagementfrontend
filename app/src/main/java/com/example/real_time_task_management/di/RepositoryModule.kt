package com.example.real_time_task_management.di

import android.app.Application
import android.content.Context
import com.example.real_time_task_management.data.remote.api.AuthApi
import com.example.real_time_task_management.data.remote.api.ServiceApi
import com.example.real_time_task_management.data.repositoryimp.AuthRepositoryImp
import com.example.real_time_task_management.data.repositoryimp.ServiceRepositoryImp
import com.example.real_time_task_management.domain.repository.AuthRepository
import com.example.real_time_task_management.domain.repository.ServiceRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi): AuthRepository {
        return AuthRepositoryImp(api)
    }

    @Provides
    @Singleton
    fun provideServiceRepository(api: ServiceApi): ServiceRepository {
        return ServiceRepositoryImp(api)
    }


}