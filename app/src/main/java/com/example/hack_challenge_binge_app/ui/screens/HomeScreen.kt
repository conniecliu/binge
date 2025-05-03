package com.example.hack_challenge_binge_app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.hack_challenge_binge_app.R
import com.example.hack_challenge_binge_app.model.DiningRepo
import com.example.hack_challenge_binge_app.ui.components.DiningCard
import com.example.hack_challenge_binge_app.ui.components.Header
import com.example.hack_challenge_binge_app.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState()

    val gradient = Brush.horizontalGradient(
        listOf(Color(0xFFF26D88), Color(0xFFFFA07A))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Dining Halls", fontSize = 24.sp, fontWeight = FontWeight.Bold)

                DateText()
            }

            // filter buttons
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("All", "Central", "North", "West").forEach { label ->
                    val isSelected = uiState.value.selectedFilter.equals(label, ignoreCase = true)
                    val background = if (isSelected) Color.Black else Color.Transparent
                    val textColor = if (isSelected) Color.White else Color.Black
                    val borderColor = Color.Black

                    Box(
                        modifier = Modifier
                            .border(BorderStroke(1.dp, borderColor), shape = RoundedCornerShape(50))
                            .background(background, shape = RoundedCornerShape(50))
                            .clickable { viewModel.loadDiningHalls(label) }
                            .padding(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            )
                    ) {
                        Text(text = label, color = textColor, fontSize = 14.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Dining Cards
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(uiState.value.diningHalls) {hall ->
                    DiningCard(diningHall = hall)
                }
            }
        }


        // Match Button at Bottom
        Button(
            onClick = { navHostController.navigate("swipe") },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .height(50.dp)
                .fillMaxWidth(0.6f)
                .clip(RoundedCornerShape(25.dp))
                .background(gradient)
        ) {
            Text("Match", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }

    Header()
}

@Composable
fun DateText() {
    val currentDate = remember {
        val formatter = SimpleDateFormat("EEE, MMMM d, yyyy", Locale.getDefault())
        formatter.format(Date())
    }

    Text("📅 $currentDate", fontSize = 14.sp)
}

//@Preview
//@Composable
//fun HomeScreenPreview() {
//
//    HomeScreen(rememberNavController(), HomeViewModel(), {}, {})
//}