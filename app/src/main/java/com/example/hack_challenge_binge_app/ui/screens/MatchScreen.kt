package com.example.hack_challenge_binge_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.hack_challenge_binge_app.ui.components.Header
import com.example.hack_challenge_binge_app.viewmodel.MatchScreenViewModel
import com.example.hack_challenge_binge_app.R

@Composable
fun MatchScreen(
    navHostController: NavHostController,
    viewModel: MatchScreenViewModel
) {

    val uiState = viewModel.uiStateFlow.collectAsState()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFFFFA07A),
                Color(0xFFF26D88),
            )
        ))
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(
                    Color.White,
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

            Spacer(modifier = Modifier.padding(40.dp))

            // MATCHED label
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFF26D88),
                                Color(0xFFFFA07A),
                                )
                        ),
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 32.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "MATCHED",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
            }

            //TODO: insert image here
            val matchedImage = uiState.value.matchedImage

            matchedImage?.let { nonNullImage ->
                Image(
                    bitmap = nonNullImage,
                    contentDescription = "Matched Dining Hall",
                    modifier = Modifier
                        .size(250.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )
            } ?: Image(
                painter = painterResource(id = R.drawable.binge_burger),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentScale = ContentScale.Crop
            )


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.CenterHorizontally)
                        .width(250.dp)
                        .height(50.dp),
                    onClick = {
                       // TODO: viewmodel here
                    },
                    colors = ButtonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFFF26D88),
                        disabledContentColor = Color(0xFFF26D88),
                        disabledContainerColor = Color.White
                    )
                ) {
                    Text(text = "Continue")
                }

                Button(
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.CenterHorizontally)
                        .width(250.dp)
                        .height(50.dp),
                    onClick = {
                        // TODO: viewmodel here 
                    },
                    colors = ButtonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFFF26D88),
                        disabledContentColor = Color(0xFFF26D88),
                        disabledContainerColor = Color.White
                    )
                ) {
                    Text(text = "Rematch")
                }

            }

        }
    }

}

@Preview
@Composable
fun MatchScreenPreview() {
    MatchScreen(rememberNavController(), MatchScreenViewModel())
}
