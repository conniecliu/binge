package com.example.hack_challenge_binge_app.model

data class UserResponse(
    val name: String,
    val username: String,
    val email: String,
    val id: Int? = null
)