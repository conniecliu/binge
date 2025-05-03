package com.example.hack_challenge_binge_app.viewmodel

import androidx.lifecycle.ViewModel
import com.example.hack_challenge_binge_app.model.DiningHall
import com.example.hack_challenge_binge_app.model.DiningRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: DiningRepo
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    data class UiState(
        val selectedFilter: String = "All",
        val diningHalls: List<DiningHall> = emptyList()
    )

    // gets dining halls based on the campus filter
    fun loadDiningHalls(filter: String) {
        _uiState.value = UiState(
            selectedFilter = filter,
            diningHalls = repo.getDiningHallsByCampus(filter)
        )
    }

    init {
        loadDiningHalls("All")
    }
}