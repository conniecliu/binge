package com.example.hack_challenge_binge_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hack_challenge_binge_app.R
import com.example.hack_challenge_binge_app.ui.components.Header
import com.example.hack_challenge_binge_app.viewmodel.ProfileScreenViewModel

@Composable
fun ProfileScreen(viewModel: ProfileScreenViewModel = hiltViewModel()) {
    val uiState = viewModel.uiStateFlow.collectAsState()
    val selected = uiState.value.selectedHall
    val allHalls = uiState.value.allHalls

    val groupedByStation = uiState.value.likedItems
        .filter { it.diningHall == selected }
        .groupBy { it.station }

    var expanded = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Header()

        // profile image and username
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val profile = uiState.value.userProfile

            if (profile != null) {
                Image(
                    bitmap = profile,
                    contentDescription = "User Profile",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentScale = ContentScale.Fit
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.cat_default_pfp),
                    contentDescription = "Default Profile",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(Color.White)
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
                        ),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = uiState.value.username,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
        }

        Column(
            modifier = Modifier
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Favorites",
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFF2A96D)
            )
            Divider(color = Color(0xFFF2A96D), thickness = 2.dp)
        }

        // Dropdown menu for the dining halls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selected,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            IconButton(onClick = { expanded.value = true }) {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
            }

            DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
                allHalls.forEach {
                    DropdownMenuItem(
                        text = { Text(it.name) },
                        onClick = {
                            expanded.value = false
                            viewModel.selectHall(it.name)
                        }
                    )
                }
            }
        }


        if (selected.isBlank()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nothing here yet... get matched first! Your liked food items will be here.",
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                groupedByStation.forEach { (station, foods) ->
                    item {
                        Text(
                            text = station,
                            color = Color.Gray,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp)
                        )
                    }

                    items(foods.chunked(3)) { row ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            row.forEach { food ->
                                Column(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color(0xFFF5F5F5))
                                        .padding(8.dp)
                                        .width(80.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Image(
                                        painter = painterResource(id = food.imageRes),
                                        contentDescription = food.name,
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = food.name,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()

}