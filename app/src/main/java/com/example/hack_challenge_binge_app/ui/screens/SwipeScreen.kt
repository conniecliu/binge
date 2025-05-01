package com.example.hack_challenge_binge_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.hack_challenge_binge_app.R
import com.example.hack_challenge_binge_app.ui.components.Header
import com.example.hack_challenge_binge_app.ui.components.TutorialOverlay
import com.example.hack_challenge_binge_app.viewmodel.SwipeViewModel
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeScreen(
    navController: NavHostController,
    viewModel: SwipeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiStateFlow.collectAsState()
    val currentFood = uiState.value.currentFood

    val swipeState = rememberSwipeableState(0)
    val width = LocalConfiguration.current.screenWidthDp.dp
    val swipeAnchors = mapOf(
        -width.value to -1,
        0f to 0,
        width.value to 1
    )

    // variable used to track if the user is on the tutorial
    var showTutorial = remember { mutableStateOf(true) }

    if (uiState.value.matchedHallName != null && uiState.value.matchedImage != null) {
        LaunchedEffect(uiState.value.matchedHallName) {
            navController.navigate("match")
        }
    }

    // Outer container
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Gradient background on top
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFA07A),
                            Color(0xFFF26D88),
                            Color(0xFFF570E3)
                        )
                    ),
                    shape = RoundedCornerShape(bottomStart = 80.dp, bottomEnd = 80.dp)
                )
        )

        Header()

        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(65.dp))

            // Food name
            Text(
                text = uiState.value.currentFood?.name.toString(),
                fontSize = 36.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
            )
        }

        // Main screen content on top of gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {

            if (currentFood != null) {
                // Swipeable image with gradient border
                Box(
                    modifier = Modifier
                        .size(350.dp)
                        .aspectRatio(1f)
                        .offset { IntOffset(swipeState.offset.value.roundToInt(), 0) }
                        .swipeable(
                            state = swipeState,
                            anchors = swipeAnchors,
                            thresholds = { _, _ -> FractionalThreshold(0.3f) },
                            orientation = Orientation.Horizontal
                        )
                        .clip(CircleShape)
                        .border(
                            width = 4.dp,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFFFA07A),
                                    Color(0xFFF26D88),
                                    Color(0xFFF570E3)
                                )
                            ),
                            shape = CircleShape
                        )
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        painter = painterResource(id = currentFood.imageRes),
                        contentDescription = currentFood.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                LaunchedEffect(swipeState.currentValue) {
                    when (swipeState.currentValue) {
                        -1 -> {
                            viewModel.swipeLeft()
                            swipeState.snapTo(0)
                        }
                        1 -> {
                            viewModel.swipeRight()
                            swipeState.snapTo(0)
                        }
                    }
                }
            }
        }
        // Tutorial overlay
        if (showTutorial.value) {
            TutorialOverlay {
                showTutorial.value = false
            }
        }
    }
}

@Preview
@Composable
fun SwipeScreenPreview() {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFA07A),
                            Color(0xFFF26D88),
                            Color(0xFFF570E3)
                        )
                    ),
                    shape = RoundedCornerShape(bottomStart = 70.dp, bottomEnd = 70.dp)
                )
        )

        Header()

        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(75.dp))

            // Food name
            Text(
                text = "Burger",
                fontSize = 36.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.binge_burger),
            contentDescription = "Preview Image",
            modifier = Modifier
                .size(350.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .border(
                    width = 4.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFA07A),
                            Color(0xFFF26D88),
                            Color(0xFFF570E3)
                        )
                    ),
                    shape = CircleShape
                )
                .background(Color.Transparent)
                .align(Alignment.Center),
            contentScale = ContentScale.Fit
        )
    }
}