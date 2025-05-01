package com.example.hack_challenge_binge_app.ui.screens

import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.hack_challenge_binge_app.ui.components.Header
import com.example.hack_challenge_binge_app.viewmodel.MatchScreenViewModel
import com.example.hack_challenge_binge_app.R
import com.example.hack_challenge_binge_app.model.DiningRepo
import kotlinx.coroutines.delay

@Composable
fun MatchScreen(
    navHostController: NavHostController,
    viewModel: MatchScreenViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiStateFlow.collectAsState()
    val uiEvent = viewModel.uiEventFlow.collectAsState()

    val context = LocalContext.current

    val gradient = Brush.horizontalGradient(
        listOf(
            Color(0xFFF26D88),
            Color(0xFFFFA07A),
        )
    )

    LaunchedEffect(uiEvent.value) {
        delay(3000L) // will remove after integrating backend
        viewModel.checkIfUserHasMatchToday()

        val fallbackProfile = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.binge_burger
        ).asImageBitmap()
        viewModel.setUserProfileImage(fallbackProfile)
    }

    LaunchedEffect(uiEvent.value) {
        uiEvent.value?.consumeSuspend { result ->
            when (result) {
                is MatchScreenViewModel.UserChoice.Continue -> {
                    navHostController.navigate("menu") {
                        popUpTo("match") { inclusive = true }
                    }
                }

                is MatchScreenViewModel.UserChoice.Rematch -> {
                    navHostController.navigate("swipe") {
                        popUpTo("match") { inclusive = true }
                    }
                }
            }
        }
    }

    AnimatedContent(
        targetState = uiState.value.loading
    ) { loading ->
        if (loading) {
            LoadingContent()
            return@AnimatedContent
        } else if (!uiState.value.hasMatchedToday) {
            NoMatchScreen(navHostController)
        } else {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFFFA07A),
                                    Color(0xFFF26D88),
                                    Color(0xFFF570E3)

                                )
                            ),
                            shape = RoundedCornerShape(bottomEnd = 200.dp, bottomStart = 200.dp)
                        )
                )

                Header()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Spacer(modifier = Modifier.padding(100.dp))

                    Text(
                        text = uiState.value.matchedHallName.toString(),
                        fontSize = 36.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.padding(30.dp))

                    // Matched image row
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // Dining hall image
                        uiState.value.matchedImage?.let { picture ->
                            Image(
                                bitmap = picture,
                                contentDescription = "Dining Hall Picture",
                                modifier = Modifier
                                    .size(150.dp)
                                    .clip(CircleShape)
                                    .background(Color.White),
                                contentScale = ContentScale.Crop
                            )
                        } ?: Image(
                            painter = painterResource(id = R.drawable.binge_burger),
                            contentDescription = "Default Profile",
                            modifier = Modifier
                                .size(150.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentScale = ContentScale.Crop
                        )


                        // Heart icon
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(gradient, CircleShape)
                                .padding(6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Heart",
                                tint = Color.White,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        uiState.value.userProfile?.let { profile ->
                            Image(
                                bitmap = profile,
                                contentDescription = "User Profile",
                                modifier = Modifier
                                    .size(150.dp)
                                    .clip(CircleShape)
                                    .background(Color.White),
                                contentScale = ContentScale.Fit
                            )
                        } ?: Image(
                            painter = painterResource(id = R.drawable.cat_default_pfp),
                            contentDescription = "Default Profile",
                            modifier = Modifier
                                .size(150.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Spacer(modifier = Modifier.padding(20.dp))

                    Text(
                        text = "Congrats its a Match!",
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
                        fontSize = 24.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp, bottom = 100.dp),
                        verticalArrangement = Arrangement.Bottom
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
                                viewModel.onContinue()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Transparent
                            ),
                        ) {
                            Text(
                                text = "Start Binge!",
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
                                viewModel.onRematch()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Transparent
                            ), contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "Rematch",
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
        
    }

}

@Composable
private fun LoadingContent() {
    var dotCount = remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(500L)
            dotCount.intValue = (dotCount.intValue + 1) % 4
        }
    }

    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Image(
            painter = painterResource(id = R.drawable.loading_logo),
            contentDescription = "loading logo",
            modifier = Modifier
                .padding(start = 16.dp)
                .size(200.dp),
            contentScale = ContentScale.Fit
        )

        AnimatedContent(
            targetState = dotCount.intValue,
            transitionSpec = {
                fadeIn(tween(150)) togetherWith (fadeOut(tween(150)))
            },
            label = "dotAnimation"
        ) { count ->
            Text(
                text = "matching you ${". ".repeat(count)}",
                fontWeight = FontWeight.Black,
                fontStyle = FontStyle.Italic,
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif
            )
        }
    }
}

@Composable
fun NoMatchScreen(navHostController: NavHostController) {
    val gradient = Brush.horizontalGradient(
        listOf(
            Color(0xFFF26D88),
            Color(0xFFFFA07A),
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            text = "No match yet today . . .",
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
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 20.dp)
        )

        Button(
            modifier = Modifier
                .padding(12.dp)
                .width(250.dp)
                .height(50.dp)
                .border(
                    width = 2.dp,
                    brush = gradient,
                    shape = RoundedCornerShape(30.dp)
                ),
            onClick = {
                navHostController.navigate("swipe")

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Transparent
            ), contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "Get Matching!",
                style = TextStyle(
                    brush = gradient,
                    fontWeight = FontWeight.Bold
                )
            )
        }

    }
}

@Preview
@Composable
fun NoMatchScreenPreview() {
    NoMatchScreen(rememberNavController())
}


@Preview
@Composable
fun LoadingContentPreview() {
    LoadingContent()
}

@Preview
@Composable
fun MatchScreenPreview() {
    MatchScreen(rememberNavController(), MatchScreenViewModel(DiningRepo(LocalContext.current)))
}
