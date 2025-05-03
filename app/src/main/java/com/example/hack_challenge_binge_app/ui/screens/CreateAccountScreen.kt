package com.example.hack_challenge_binge_app.ui.screens

import android.graphics.Color.alpha
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.hack_challenge_binge_app.R
import com.example.hack_challenge_binge_app.viewmodel.CreateAccountViewModel

@Composable
fun CreateAccountScreen(
    navHostController: NavHostController,
    viewModel: CreateAccountViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiStateFlow.collectAsState()
    val uiEvent = viewModel.uiEventFlow.collectAsState()

    val passwordVisible = remember { mutableStateOf(false) }

    val gradient = Brush.horizontalGradient(
        listOf(
            Color(0xFFF26D88),
            Color(0xFFFFA07A),
        )
    )

    val context = LocalContext.current

    LaunchedEffect(uiEvent.value) {
        uiEvent.value?.consume { result ->
            when (result) {
                is CreateAccountViewModel.CreateSuccess.Success -> {
                    navHostController.navigate("login")
                }
                is CreateAccountViewModel.CreateSuccess.Error -> {
                    Toast.makeText(context, result.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFFFA07A),
                        Color(0xFFF26D88),
                    )
                )
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(800.dp)
                .background(
                    Color.White,
                    shape = RoundedCornerShape(bottomEnd = 200.dp, bottomStart = 200.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(30.dp)
                    .height(55.dp)
                    .padding(top = 20.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fork),
                    modifier = Modifier
                        .size(300.dp)
                        .align(Alignment.TopCenter),
                    contentDescription = "fork",
                    contentScale = ContentScale.Fit
                )
            }

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly

            ) {
                Text(
                    text = "Create Account",
                    style = TextStyle(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFFFA07A),
                                Color(0xFFF26D88),
                                Color(0xFFF570E3)
                            )
                        ),
                        fontWeight = FontWeight.Bold
                    ),
                    fontSize = 34.sp,
                    modifier = Modifier
                        .padding(24.dp)
                )

                // email
                TextField(
                    value = uiState.value.email,
                    onValueChange = {newText ->
                        viewModel.updateEmail(newText)
                    },
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    placeholder = {
                        Text("Email")
                    },
                    shape = RoundedCornerShape(7.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color(0xFFF26D88).copy(alpha =0.3f),
                        unfocusedContainerColor = Color(0xFFF26D88).copy(alpha =0.3f),
                        disabledContainerColor = Color(0xFFF26D88).copy(alpha =0.3f),
                        unfocusedPlaceholderColor = Color.Gray,
                        focusedPlaceholderColor = Color.Gray,
                        focusedTextColor = Color.Black
                    )
                )

                // name
                TextField(
                    value = uiState.value.name,
                    onValueChange = {newText ->
                        viewModel.updateName(newText)
                    },
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    placeholder = {
                        Text("Name")
                    },
                    shape = RoundedCornerShape(7.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color(0xFFF26D88).copy(alpha =0.3f),
                        unfocusedContainerColor = Color(0xFFF26D88).copy(alpha =0.3f),
                        disabledContainerColor = Color(0xFFF26D88).copy(alpha =0.3f),
                        unfocusedPlaceholderColor = Color.Gray,
                        focusedPlaceholderColor = Color.Gray,
                        focusedTextColor = Color.Black
                    )
                )

                // user name
                TextField(
                    value = uiState.value.username,
                    onValueChange = {newText ->
                        viewModel.updateUsername(newText)
                    },
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    placeholder = {
                        Text("Username")
                    },
                    shape = RoundedCornerShape(7.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color(0xFFF26D88).copy(alpha =0.3f),
                        unfocusedContainerColor = Color(0xFFF26D88).copy(alpha =0.3f),
                        disabledContainerColor = Color(0xFFF26D88).copy(alpha =0.3f),
                        unfocusedPlaceholderColor = Color.Gray,
                        focusedPlaceholderColor = Color.Gray,
                        focusedTextColor = Color.Black
                    )
                )

                // password
                TextField(
                    value = uiState.value.password,
                    onValueChange = { newText ->
                        viewModel.updatePassword(newText)
                    },
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    placeholder = {
                        Text("Password")
                    },
                    shape = RoundedCornerShape(7.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color(0xFFF26D88).copy(alpha =0.3f),
                        unfocusedContainerColor = Color(0xFFF26D88).copy(alpha =0.3f),
                        disabledContainerColor = Color(0xFFF26D88).copy(alpha =0.3f),
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

                // confirm password
                TextField(
                    value = uiState.value.confirmPass,
                    onValueChange = { newText ->
                        viewModel.updateConfirmPass(newText)
                    },
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    placeholder = {
                        Text("Confirm password")
                    },
                    shape = RoundedCornerShape(7.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color(0xFFF26D88).copy(alpha =0.3f),
                        unfocusedContainerColor = Color(0xFFF26D88).copy(alpha =0.3f),
                        disabledContainerColor = Color(0xFFF26D88).copy(alpha =0.3f),
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
                
                // Create button
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
                        viewModel.onCreate()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Transparent
                    ), contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Create",
                        style = TextStyle(
                            brush = gradient,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

            }

        }

    }

}

@Preview
@Composable
fun CreateAccountScreenPreview() {
    CreateAccountScreen(rememberNavController())
}