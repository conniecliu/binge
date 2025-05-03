package com.example.hack_challenge_binge_app.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hack_challenge_binge_app.ui.screens.CreateAccountScreen
import com.example.hack_challenge_binge_app.ui.screens.LoginScreen
import com.example.hack_challenge_binge_app.ui.screens.HomeScreen
import com.example.hack_challenge_binge_app.ui.screens.MatchScreen
import com.example.hack_challenge_binge_app.ui.screens.MenuScreen
import com.example.hack_challenge_binge_app.ui.screens.ProfileScreen
import com.example.hack_challenge_binge_app.ui.screens.SwipeScreen
import com.example.hack_challenge_binge_app.ui.screens.WelcomeScreen
import com.example.hack_challenge_binge_app.ui.theme.Hack_challenge_binge_appTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Hack_challenge_binge_appTheme {
                val navController = rememberNavController()

                val tabs = listOf("home", "match", "profile")

                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry.value?.destination?.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (
                            currentRoute != "login" &&
                            currentRoute != "welcome" &&
                            currentRoute != "create" &&
                            currentRoute != null) {
                            NavigationBar (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .drawBehind {
                                        val strokeWidth = 2.dp.toPx()
                                        drawLine(
                                            color = Color.Gray,
                                            start = Offset(0f, 0f + strokeWidth / 2),
                                            end = Offset(size.width, 0f + strokeWidth / 2),
                                            strokeWidth = strokeWidth
                                        )
                                    },
                                containerColor = Color.White,
                            ) {
                                tabs.forEach { tabRoute ->
                                    NavigationBarItem(
                                        selected = currentRoute == tabRoute,
                                        onClick = {
                                            navController.navigate(tabRoute) {
                                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = when (tabRoute) {
                                                    "home" -> Icons.Default.Home
                                                    "profile" -> Icons.Default.AccountCircle
                                                    "match" -> Icons.Default.Favorite
                                                    else -> Icons.Default.Star
                                                },
                                                contentDescription = tabRoute
                                            )
                                        },
                                        label = { Text(tabRoute.uppercase()) }
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = "welcome"
                        ) {
                            composable("welcome") {
                                WelcomeScreen(navController)
                            }

                            composable("login") {
                                LoginScreen(navController)
                            }

                            composable("create") {
                                CreateAccountScreen(navController)
                            }

                            composable("home") {
                                HomeScreen(navController)
                            }
                            composable("profile") {
                                ProfileScreen()
                            }

                            composable("match") {
                                MatchScreen(navController)
                            }

                            composable("swipe") {
                                SwipeScreen(navController)
                            }

                            composable("menu") {
                                MenuScreen()
                            }

                        }
                    }
                }
            }
        }
    }
}
