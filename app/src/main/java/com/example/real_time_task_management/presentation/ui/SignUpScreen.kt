package com.example.real_time_task_management.presentation.ui


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.real_time_task_management.domain.model.User
import com.example.real_time_task_management.dto.requestdto.RegisterReqDTO
import com.example.real_time_task_management.navigation.Screens
import com.example.real_time_task_management.presentation.viewmodel.AuthViewModel


@Composable
fun SignUpScreen(
    navController: NavHostController,
) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val viewModel: AuthViewModel = hiltViewModel()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) Color.Black else Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Create Account", fontSize = 30.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("UserName") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val emailTrim = email.trim()
                    val passwordTrim = password.trim()
                    val usernameTrim = username.trim()
                    if (emailTrim.isEmpty() || passwordTrim.isEmpty() || usernameTrim.isEmpty()) {
                        errorMessage = "Please fill in all fields"
                    } else
                        viewModel.register(
                            RegisterReqDTO(
                                username = usernameTrim,
                                email = emailTrim,
                                password = passwordTrim
                            ),
                            context = context,
                            navController = navController
                        )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign Up")
            }

            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = {
                navController.navigate(Screens.LoginScreen.route) {
                    popUpTo(Screens.LoginScreen.route) {
                        inclusive = true
                    }
                }
            }) {
                Text("Already have an account? Login")
            }
        }
    }
}
