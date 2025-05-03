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
        FoodItem("Pizza", R.drawable.pizza, "Morrison", "Pizza Station"),
        FoodItem("Caesar Salad", R.drawable.caesar_salad, "Morrison", "Salad Bar"),
        FoodItem("Stir-fry", R.drawable.stir_fry, "Morrison", "Iron Grill"),

        FoodItem("Pho", R.drawable.pho, "North Star", "Create"),
        FoodItem("Burger", R.drawable.burger, "North Star", "Grill"),
        FoodItem("Veggie Stir Fry", R.drawable.veggie, "North Star", "Wok"),

        FoodItem("Tostadas", R.drawable.tostada, "Risley", "Chef's Table"),
        FoodItem("Poke Bowl", R.drawable.poke, "Risley", "Wok"),
        FoodItem("Marble cake", R.drawable.marble, "Risley", "Desserts"),

        FoodItem("Curry", R.drawable.curry, "Bethe Jansen", "Chef's Table"),
        FoodItem("Ginger Soy Chicken", R.drawable.chicken, "Bethe Jansen", "Wok"),
        FoodItem("Margherita Pizza", R.drawable.mar_pizza, "Bethe Jansen", "Pizza Station"),

        FoodItem("BBQ Pulled Pork", R.drawable.pulled_pork, "Becker", "Chef's Table"),
        FoodItem("Tiramisu", R.drawable.tiramisu, "Becker", "Desserts"),
        FoodItem("Clam Chowder", R.drawable.chowder, "Becker", "Soup"),

        FoodItem("Pad Thai", R.drawable.pad_thai, "Okenshields", "Wok"),
        FoodItem("Grilled Cheese", R.drawable.grilled_cheese, "Okenshields", "Grill"),
        FoodItem("Curly Fries", R.drawable.curly_fries, "Okenshields", "Grill"),
        )

    private var todayMatch: MatchResultDummy? = null
    private val likedItems = mutableListOf<FoodItem>()

    fun saveLikedItem(item: FoodItem) {
        likedItems.add(item)
    }

    fun getLikedItems(): List<FoodItem> = likedItems

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
            "Becker" -> R.drawable.becker
            "Morrison" -> R.drawable.morrison
            "Okenshields" -> R.drawable.oakenshields
            else -> R.drawable.binge_burger
        }
        return BitmapFactory.decodeResource(context.resources, resId).asImageBitmap()
    }

    suspend fun getTodayMatchOrNull(): MatchResultDummy? {
        delay(300) // simulate backend delay
        return todayMatch
    }

    fun getAllDiningHalls(): List<DiningHall> {
        return listOf(
            DiningHall(
                name = "Bethe Jansen",
                campusLocation = "West Campus",
                imageResId = R.drawable.jansen,
                openHours = "Dinner 5:00–8:30",
                menu = foodItems.filter { it.diningHall == "Bethe Jansen" }
            ),
            DiningHall(
                name = "Becker",
                campusLocation = "West Campus",
                imageResId = R.drawable.becker,
                openHours = "Dinner 5:00–8:30",
                menu = foodItems.filter { it.diningHall == "Becker" }
            ),
            DiningHall(
                name = "North Star",
                campusLocation = "North Campus",
                imageResId = R.drawable.north_star,
                openHours = "Dinner 5:30–10:30",
                menu = foodItems.filter { it.diningHall == "North Star" }
            ),
            DiningHall(
                name = "Morrison",
                campusLocation = "North Campus",
                imageResId = R.drawable.morrison,
                openHours = "Dinner 5:00–8:30",
                menu = foodItems.filter { it.diningHall == "Morrison" }
            ),
            DiningHall(
                name = "Risley",
                campusLocation = "North Campus",
                imageResId = R.drawable.risley,
                openHours = "Dinner 5:00–7:00",
                menu = foodItems.filter { it.diningHall == "Risley" }
            ),
            DiningHall(
                name = "Okenshields",
                campusLocation = "Central Campus",
                imageResId = R.drawable.oakenshields,
                openHours = "Dinner 5:00–8:30",
                menu = foodItems.filter { it.diningHall == "Okenshields" }
        ),

        )
    }

    fun getDiningHallsByCampus(filter: String): List<DiningHall> {
        return when (filter.lowercase()) {
            "central" -> getAllDiningHalls().filter { it.campusLocation.contains("Central", ignoreCase = true) }
            "north" -> getAllDiningHalls().filter { it.campusLocation.contains("North", ignoreCase = true) }
            "west" -> getAllDiningHalls().filter { it.campusLocation.contains("West", ignoreCase = true) }
            else -> getAllDiningHalls()
        }
    }



}