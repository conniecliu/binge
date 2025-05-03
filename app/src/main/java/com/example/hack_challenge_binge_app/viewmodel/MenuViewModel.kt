package com.example.hack_challenge_binge_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hack_challenge_binge_app.model.DiningHall
import com.example.hack_challenge_binge_app.model.DiningRepo
import com.example.hack_challenge_binge_app.model.FoodItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val repo: DiningRepo
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiStateFlow: StateFlow<UiState> = _uiState

    data class UiState(
        val diningHall: DiningHall? = null,
        val selectedHall: String = "",
        val likedItems: List<FoodItem> = emptyList()
    )

    fun loadMenuScreen() {
        viewModelScope.launch {
            val match = repo.getTodayMatchOrNull()
            if (match != null) {
                val hall = repo.getAllDiningHalls().find { it.name == match.matchedHallName }
                val liked = repo.getLikedItems()

                if (hall != null) {
                    _uiState.value = UiState(
                        diningHall = hall,
                        selectedHall = hall.name,
                        likedItems = liked
                    )
                }
            }
        }
    }
}