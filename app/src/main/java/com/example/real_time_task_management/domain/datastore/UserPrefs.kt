package com.example.real_time_task_management.domain.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject


private val Context.dataStore by preferencesDataStore("user_prefs")

class UserPrefs @Inject constructor(private val context: Context) {


    private val accessTokenKey = stringPreferencesKey("access_token")
    private val isLoggedInKey = booleanPreferencesKey("is_logged_in")

    private val refreshTokenKey = stringPreferencesKey("refresh_token")

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[isLoggedInKey] ?: false
    }
    val accessToken: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[accessTokenKey]
    }

    val refreshToken: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[refreshTokenKey]
    }

    suspend fun saveRefreshToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[refreshTokenKey] = token
        }
    }

    suspend fun getRefreshToken(): String? {
        return refreshToken.first()
    }

    suspend fun saveAccessToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[accessTokenKey] = token
        }
    }

    suspend fun changeLoginStatus(isLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[isLoggedInKey] = isLoggedIn
        }
    }

    suspend fun getAccessToken(): String? {
        return accessToken.first()
    }

    suspend fun getIsLoggedIn(): Boolean {
        return isLoggedIn.first()
    }

    suspend fun clearLoginData() {
        context.dataStore.edit { preferences ->
            preferences.remove(accessTokenKey)
            preferences.remove(isLoggedInKey)
            preferences.remove(refreshTokenKey)
        }
    }


}