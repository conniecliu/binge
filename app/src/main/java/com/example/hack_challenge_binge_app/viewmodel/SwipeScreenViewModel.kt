package com.example.hack_challenge_binge_app.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hack_challenge_binge_app.model.DiningRepo
import com.example.hack_challenge_binge_app.model.FoodItem
import com.example.hack_challenge_binge_app.model.MatchResultDummy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SwipeViewModel @Inject constructor(
    private val repository: DiningRepo
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiStateFlow = _uiState

    private val swipeCounts = mutableMapOf<String, Int>()
    private var foodQueue = repository.getAllFoodItems()
    private var currentIndex = 0


    data class UiState(
        val currentFood: FoodItem? = null,
        val matchedHallName: String? = null,
        val matchedImage: ImageBitmap? = null
    )

    init {
        _uiState.value = _uiState.value.copy(
            currentFood = foodQueue.getOrNull(currentIndex)
        )
    }

    fun swipeLeft() = advanceSwipe()

    fun swipeRight() {
        val current = _uiState.value.currentFood ?: return
        swipeCounts[current.diningHall] = (swipeCounts[current.diningHall] ?: 0) + 1
        advanceSwipe()
    }

    private fun advanceSwipe() {
        currentIndex++
        val next = foodQueue.getOrNull(currentIndex)

        if (next == null || currentIndex >= 10) {
            val topMatch = swipeCounts.maxByOrNull { it.value }?.key ?: return
            matchAndLoadImage(topMatch)
        } else {
            _uiState.value = _uiState.value.copy(currentFood = next)
        }
    }

    private fun matchAndLoadImage(hall: String) {
        viewModelScope.launch {
            val bitmap = repository.getImageForDiningHall(hall)

            MatchResultDummy.matchedHallName = hall
            MatchResultDummy.matchedImage = bitmap

            repository.setTodayMatch(MatchResultDummy)

            _uiState.value = _uiState.value.copy(
                matchedHallName = hall,
                matchedImage = bitmap,
                currentFood = null
            )
        }
    }
}