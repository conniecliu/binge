package com.example.hack_challenge_binge_app.model

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.example.hack_challenge_binge_app.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiningRepo @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val foodItems = listOf(
        FoodItem("Pizza", android.R.drawable.ic_menu_camera, "Bethe Jansen", "Grill"),
        FoodItem("Salad", android.R.drawable.ic_menu_gallery, "North Star", "Salad"),
        FoodItem("Burger", R.drawable.binge_burger, "North Star", "Grill",),
        FoodItem("Tacos", android.R.drawable.ic_menu_call, "Bethe Jansen", "Chef's Table"),
        FoodItem("Sushi", android.R.drawable.ic_menu_compass, "Risley", "Wok"),
        FoodItem("Curry", android.R.drawable.ic_dialog_info, "Risley", "Chef's Table"),
        )

    private var todayMatch: MatchResultDummy? = null

    fun setTodayMatch(match: MatchResultDummy) {
        todayMatch = match
    }

    fun getAllFoodItems(): List<FoodItem> = foodItems.shuffled()

    suspend fun getImageForDiningHall(name: String): ImageBitmap {
        delay(300)
        val resId = when (name) {
            "Bethe Jansen" -> R.drawable.jansen
            "North Star" -> R.drawable.north_star
            "Risley" -> R.drawable.risley
            else -> R.drawable.binge_burger
        }
        return BitmapFactory.decodeResource(context.resources, resId).asImageBitmap()
    }

    suspend fun getTodayMatchOrNull(): MatchResultDummy? {
        delay(300) // simulate backend delay
        return todayMatch
    }
}