package com.example.real_time_task_management.presentation.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.real_time_task_management.domain.datastore.UserPrefs
import com.example.real_time_task_management.domain.model.User
import com.example.real_time_task_management.domain.repository.AuthRepository
import com.example.real_time_task_management.dto.requestdto.LoginReqDTO
import com.example.real_time_task_management.dto.requestdto.RegisterReqDTO
import com.example.real_time_task_management.dto.responsedto.LoginResponseDTO
import com.example.real_time_task_management.dto.responsedto.RegisterResponseDTO
import com.example.real_time_task_management.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    val userPrefs: UserPrefs,
) : ViewModel() {
    val userRole = runBlocking {userPrefs.getUser()?.user?.userRole.toString()}
    val isLoggedIn: Flow<Boolean> = userPrefs.isLoggedIn


    fun register(req: RegisterReqDTO, context: Context, navController: NavController) {
        viewModelScope.launch {
            try {
                repository.register(req)
                Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()
                navController.navigate(Screens.LoginScreen.route)
            } catch (e: Exception) {
                Toast.makeText(context, "Registration Failed ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    private val _loginResponse = MutableStateFlow<LoginResponseDTO?>(null)
    val loginResponse: StateFlow<LoginResponseDTO?> = _loginResponse

    fun login(loginReqDTO: LoginReqDTO, context: Context, navController: NavController) {
        viewModelScope.launch {
            try {
                val response = repository.login(loginReqDTO)
                userPrefs.saveAccessToken(response.accessToken)
                userPrefs.saveRefreshToken(response.refreshToken)
                _loginResponse.value = response
                userPrefs.changeLoginStatus(true)
                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                navController.navigate(Screens.ProjectsScreen.route)
                userPrefs.saveUser(loginResponse.value!!)
                Log.d("TAG", "login: ${loginResponse.value}")
            } catch (e: Exception) {
                _loginResponse.value = null
                Toast.makeText(context, "Login Failed ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun logOut(context: Context, navController: NavController) {
        viewModelScope.launch {
            try {
                userPrefs.clearLoginData()
                Toast.makeText(context, "Logout Successful", Toast.LENGTH_SHORT).show()
                navController.navigate(Screens.LoginScreen.route)
            } catch (e: Exception) {
                Toast.makeText(context, "Logout Failed ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }

}