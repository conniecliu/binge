package com.example.hack_challenge_binge_app.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardDoubleArrowLeft
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun TutorialOverlay(onTutorialDone: () -> Unit) {
    var step = remember { mutableIntStateOf(0) }

    if (step.intValue < 2) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x88000000))
                .zIndex(1f)
                .clickable {
                    step.intValue++
                    if (step.intValue >= 2) {
                        onTutorialDone()
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                targetState = step.intValue,
                transitionSpec = {
                    fadeIn(tween(300)) togetherWith fadeOut(tween(300))
                },
                label = "tutorialStep"
            ) { targetStep ->
                when (targetStep) {
                    0 -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.KeyboardDoubleArrowLeft,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                        Text("Swipe left to reject", color = Color.White)

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Tap anywhere to continue", color = Color.White, fontSize = 12.sp)
                    }

                    1 -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.KeyboardDoubleArrowRight,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                        Text("Swipe right to like", color = Color.White)

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Tap anywhere to finish", color = Color.White, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}