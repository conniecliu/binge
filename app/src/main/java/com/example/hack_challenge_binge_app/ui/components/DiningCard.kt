package com.example.hack_challenge_binge_app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hack_challenge_binge_app.R
import com.example.hack_challenge_binge_app.model.DiningHall
import com.example.hack_challenge_binge_app.model.FoodItem

@Composable
fun DiningCard(
    diningHall: DiningHall,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
        ){
            Image(
                painter = painterResource(id = diningHall.imageResId),
                contentDescription = diningHall.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(12.dp)

            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = diningHall.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "open",
                        color = Color(0xFF00C853),
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = diningHall.openHours,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview
@Composable
fun DiningCardPreview() {
     val foodItems = listOf(
        FoodItem("Pizza", android.R.drawable.ic_menu_camera, "Morrison", "Pizza Station"),
        FoodItem("Caesar Salad", R.drawable.binge_burger, "Morrison", "Salad Bar"),
        FoodItem("Stir-fry", R.drawable.binge_burger, "Morrison", "Iron Grill"),
        )

    DiningCard(
        DiningHall(
            name = "Morrison Dining",
            campusLocation = "North Campus",
            imageResId = R.drawable.morrison,
            openHours = "Dinner 5:00–8:30",
            menu = foodItems.filter { it.diningHall == "Morrison" }
        )
    )
}