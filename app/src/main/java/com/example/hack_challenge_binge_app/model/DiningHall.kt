package com.example.hack_challenge_binge_app.model

data class DiningHall (
    val name: String,
    val campusLocation: String,
    val imageResId: Int,
    val openHours: String,
    val menu: List<FoodItem>
)