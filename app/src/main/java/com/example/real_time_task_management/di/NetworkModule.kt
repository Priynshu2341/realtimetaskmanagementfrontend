package com.example.real_time_task_management.di

import androidx.navigation.Navigator
import com.example.real_time_task_management.data.remote.api.AuthApi
import com.example.real_time_task_management.data.remote.api.ServiceApi
import com.example.real_time_task_management.data.remote.interceptor.AuthInterceptor
import com.example.real_time_task_management.data.remote.interceptor.TokenAuthenticator
import com.example.real_time_task_management.domain.datastore.UserPrefs
import com.example.real_time_task_management.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Named
import jakarta.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://10.0.2.2:8080/"

    @Provides
    @Singleton
    fun provideAuthInterceptor(userPrefs: UserPrefs): AuthInterceptor {
        return AuthInterceptor(userPrefs)
    }

    @Provides
    @Singleton
    fun provideAuthenticator(userPrefs: UserPrefs,authRepository: AuthRepository): TokenAuthenticator {
        return TokenAuthenticator(authRepository, userPrefs)
    }

    @Provides
    @Singleton
    fun provideOKHttpClient(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .authenticator(tokenAuthenticator)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }


    @Provides
    @Singleton
    @Named("Retrofit")
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    @Named("ServiceApi")
    fun provideServiceApi(@Named("Retrofit")retrofit: Retrofit): ServiceApi {
        return retrofit.create(ServiceApi::class.java)
    }

    @Provides
    @Singleton
    @Named("RefreshRetrofit")
    fun provideRefreshRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("AuthApi")
    fun provideRefreshAuthApi(@Named("RefreshRetrofit") refreshRetrofit: Retrofit): AuthApi {
        return refreshRetrofit.create(AuthApi::class.java)
    }
}