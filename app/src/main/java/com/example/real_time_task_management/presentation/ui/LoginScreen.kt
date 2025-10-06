package com.example.real_time_task_management.presentation.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.real_time_task_management.dto.requestdto.LoginReqDTO
import com.example.real_time_task_management.dto.responsedto.LoginResponseDTO
import com.example.real_time_task_management.navigation.Screens
import com.example.real_time_task_management.presentation.viewmodel.AuthViewModel


@Composable
fun LoginScreen(
    navController: NavController,
) {

    val viewmodel: AuthViewModel = hiltViewModel()
    val context = LocalContext.current
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier.background(
            if (isSystemInDarkTheme()) Color.Black else Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                "Login",
                fontWeight = FontWeight.Bold,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 40.sp
            )
            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("User Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    viewmodel.login(
                        LoginReqDTO(username = userName, password = password),
                        context = context,
                        navController = navController
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            Spacer(Modifier.height(10.dp))

            Text(
                "SignUp ",
                fontWeight = FontWeight.Bold,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                modifier = Modifier.clickable(
                    true,
                    onClick = {
                        navController.navigate(
                            Screens.SignUpScreen.route
                        )
                    }
                )
            )
            TextButton(onClick = {}) {
                Text("Forgot Password")
            }

            if (isLoading) {
                CircularProgressIndicator(Modifier.padding(top = 16.dp))
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen( navController = NavController(LocalContext.current))
}


@Composable
fun FakeLoginScreen() {
    var email by remember { mutableStateOf("test@email.com") }
    var password by remember { mutableStateOf("123456") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("Invalid password") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(20.dp))

        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("Login")
        }

        Spacer(Modifier.height(10.dp))

        Text(" IF New User")

        Text(
            "Sing up ", modifier = Modifier.clickable(true, onClick = {})

        )


        if (isLoading) {
            CircularProgressIndicator(Modifier.padding(top = 16.dp))
        }

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    }
}
