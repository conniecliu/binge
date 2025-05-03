package com.example.hack_challenge_binge_app.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.hack_challenge_binge_app.R
import com.example.hack_challenge_binge_app.viewmodel.WelcomeViewModel

@Composable
fun WelcomeScreen(
    navHostController: NavHostController,
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    val uiEvent = viewModel.uiEventFlow.collectAsState()

    val gradient = Brush.horizontalGradient(
        listOf(
            Color(0xFFF26D88),
            Color(0xFFFFA07A),
        )
    )

    LaunchedEffect(uiEvent.value) {
        uiEvent.value?.consumeSuspend { result ->
            when (result) {
                is WelcomeViewModel.Choice.Login -> {
                    navHostController.navigate("login") {
                        popUpTo("welcome") { inclusive = true }
                    }
                }
                is WelcomeViewModel.Choice.Signup -> {
                    navHostController.navigate("create") {
                        popUpTo("welcome") { inclusive = true }
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {

        // gradient circles background
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val radius = size.maxDimension / 2

            drawCircle(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFA07A).copy(1f),
                        Color(0xFFF570E3)
                    )
                ),
                radius = radius,
                center = Offset(0f, 300f),
                )

            drawCircle(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFFFA07A).copy(1f),
                        Color(0xFFF26D88).copy(1f),
                        Color(0xFFF570E3).copy(1f)

                    ),
                ),
                radius = radius / 1.5f,
                center = Offset(size.width, 0f)
            )

            // Circle 3
            drawCircle(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFA07A),
                        Color(0xFFF26D88).copy(0.5f),
                        Color(0xFFF570E3).copy(0.8f),
                        ),
                ),
                radius = radius / 3,
                center = Offset(0f, size.height / 1.8f)
            )

        }

    }

    // Logo + text
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 56.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Image(
            painter = painterResource(id = R.drawable.white_burger),
            modifier = Modifier
                .padding(30.dp)
                .size(120.dp),
            contentDescription = "logo",
        )

        Text(
            text = "Welcome to",
            color = Color.White,
            fontSize = 48.sp,
            fontWeight = FontWeight.SemiBold,
        )

        Image(
            painter = painterResource(id = R.drawable.binge),
            modifier = Modifier
                .size(120.dp),
            contentDescription = "logo",
        )

        Spacer(modifier = Modifier.padding(12.dp))

        // Buttons
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Button(
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.CenterHorizontally)
                    .width(250.dp)
                    .height(50.dp)
                    .border(
                        width = 2.dp,
                        brush = gradient,
                        shape = RoundedCornerShape(30.dp)
                    ),
                onClick = {
                    viewModel.onSignUp()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Transparent
                ),
            ) {
                androidx.compose.material3.Text(
                    text = "Sign up",
                    style = TextStyle(
                        brush = gradient,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Button(
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.CenterHorizontally)
                    .width(250.dp)
                    .height(50.dp)
                    .border(
                        width = 2.dp,
                        brush = gradient,
                        shape = RoundedCornerShape(30.dp)
                    ),
                onClick = {
                    viewModel.onLogin()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Transparent
                ), contentPadding = PaddingValues(0.dp)
            ) {
                androidx.compose.material3.Text(
                    text = "Log in",
                    style = TextStyle(
                        brush = gradient,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Image(
                painter = painterResource(id = R.drawable.fork),
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.CenterHorizontally),
                contentDescription = "fork",
            )

        }
    }



}

@Preview
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(rememberNavController())
}