package com.example.hack_challenge_binge_app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hack_challenge_binge_app.R

@Composable
fun Header() {
    Column(
        modifier = Modifier
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFF26D88),
                        Color(0xFFFFA07A),
                    )
                )
            )
            .fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.binge_logo),
            modifier = Modifier
                .padding(start = 16.dp)
                .size(60.dp),
            contentDescription = "binge logo",
        )

    }

}

@Preview
@Composable
fun HeaderPreview() {
    Header()

}
