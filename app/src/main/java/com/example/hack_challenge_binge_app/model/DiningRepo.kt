package com.example.hack_challenge_binge_app.model

data class DiningRepo(val name: String, val location: String) {
    val mockDiningList = listOf(
        DiningRepo("Cook", "West"),
        DiningRepo("Novick", "South"),
        DiningRepo("Zeus", "Central"),
        DiningRepo("Macs", "Central"),
        DiningRepo("Trillium", "North")
    )
}