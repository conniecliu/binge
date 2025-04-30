package com.example.hack_challenge_binge_app.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hack_challenge_binge_app.ui.screens.LoginScreen
import com.example.hack_challenge_binge_app.ui.screens.HomeScreen
import com.example.hack_challenge_binge_app.ui.screens.MatchScreen
import com.example.hack_challenge_binge_app.ui.screens.MenuScreen
import com.example.hack_challenge_binge_app.ui.screens.ProfileScreen
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

                val tabs = listOf("home", "profile", "match")

                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry.value?.destination?.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (currentRoute != "login" && currentRoute != null) {
                            NavigationBar {
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
                            startDestination = "login"
                        ) {
                            composable("login") {
                                LoginScreen(navController)
                            }
                            composable("home") {
                                HomeScreen(
                                    navController,
                                    onDiningClick = { dining ->
                                        navController.navigate("menu/$dining")
                                    }
                                )
                            }
                            composable("profile") {
                                ProfileScreen()
                            }

                            composable("match") {
                                MatchScreen()
                            }

                            composable("menu/{dining}") { backStackEntry ->
                                val dining = backStackEntry.arguments?.getString("dining") ?: ""
                                MenuScreen(navController, diningChosen = dining)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Hack_challenge_binge_appTheme {
        Greeting("Android")
    }
}