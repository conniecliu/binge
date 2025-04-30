package com.example.hack_challenge_binge_app.ui.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.hack_challenge_binge_app.viewmodel.LoginViewModel

@Composable
fun MenuScreen(
    navHostController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel(),
    diningChosen: String
) {}