package com.example.hack_challenge_binge_app.ui.screens

import android.widget.Toast
import com.example.hack_challenge_binge_app.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.hack_challenge_binge_app.viewmodel.LoginViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.DeviceFontFamilyName
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiStateFlow.collectAsState()
    val uiEvent = viewModel.uiEventFlow.collectAsState()

    val passwordVisible = remember { mutableStateOf(false) }

    LaunchedEffect(uiEvent.value) {
        uiEvent.value?.consumeSuspend { result ->
            navHostController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
    ) {

    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(700.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFA07A),
                        Color(0xFFF26D88),
                        Color(0xFFF570E3)
                    )
                ),
                shape = RoundedCornerShape(bottomStart = 1000.dp)
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_logo),
            modifier = Modifier
                .padding(30.dp)
                .size(90.dp)
                .align(Alignment.TopEnd),
            contentDescription = "logo",
        )

        // Login block
        Column(
            modifier = Modifier
                .padding(18.dp)
                .padding(top = 100.dp)
                .fillMaxSize(),
        ) {
            Text(
                text = "Log in",
                color = Color.White,
                modifier = Modifier
                    .padding(18.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
            )

            TextField(
                value = uiState.value.username,
                onValueChange = {newText ->
                    viewModel.updateUsername(newText)
                },
                modifier = Modifier
                    .padding(18.dp)
                    .fillMaxWidth(),
                placeholder = {
                    Text("Email/Username")
                },
                shape = RoundedCornerShape(7.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    unfocusedPlaceholderColor = Color.Gray,
                    focusedPlaceholderColor = Color.Gray,
                    focusedTextColor = Color.Black
                )
            )

            TextField(
                value = uiState.value.password,
                onValueChange = { newText ->
                    viewModel.updatePassword(newText)
                },
                modifier = Modifier
                    .padding(18.dp)
                    .fillMaxWidth(),
                placeholder = {
                    Text("Password")
                },
                shape = RoundedCornerShape(7.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    unfocusedPlaceholderColor = Color.Gray,
                    focusedPlaceholderColor = Color.Gray,
                    focusedTextColor = Color.Black
                ),
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible.value) {
                        Icons.Default.Visibility
                    } else {
                        Icons.Default.VisibilityOff
                    }

                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        Icon(imageVector = image, contentDescription = if (passwordVisible.value) "Hide password" else "Show password")
                    }
                }
            )

            Spacer(modifier = Modifier
                .padding(20.dp)
            )

            Button(
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.CenterHorizontally)
                    .width(250.dp)
                    .height(50.dp),
                onClick = {
                    viewModel.onLogin()
                },
                colors = ButtonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFFF570E3),
                    disabledContentColor = Color(0xFFF570E3),
                    disabledContainerColor = Color.White
                )
            ) {
                Text(text = "Log in")
            }

        }

    }
}

@Preview
@Composable
fun LoginScreenPreview(){
    LoginScreen(rememberNavController(), LoginViewModel())
}