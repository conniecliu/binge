package com.example.hack_challenge_binge_app.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hack_challenge_binge_app.model.DiningHall
import com.example.hack_challenge_binge_app.model.DiningRepo
import com.example.hack_challenge_binge_app.model.FoodItem
import com.example.hack_challenge_binge_app.model.UIEvent
import com.example.hack_challenge_binge_app.model.UserSession
import com.example.hack_challenge_binge_app.retrofit.RetrofitInstance
import com.example.hack_challenge_binge_app.viewmodel.LoginViewModel.LoginSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val repo: DiningRepo,

) : ViewModel() {
    val uiStateFlow: MutableStateFlow<UiState> = MutableStateFlow(UiState())

    data class UiState(
        val username: String = "Unknown User",
        val userProfile: ImageBitmap? = null,
        val selectedHall: String = "",
        val allHalls: List<DiningHall> = listOf(),
        val likedItems: List<FoodItem> = listOf()
    )


    fun selectHall(hall: String) {
        uiStateFlow.value = uiStateFlow.value.copy(
            selectedHall = hall
        )
    }

    fun loadListOfHalls() {
        uiStateFlow.value = uiStateFlow.value.copy(
            allHalls = repo.getAllDiningHalls()
        )
    }

    fun loadLikedItems() {
        val likedItems = repo.getLikedItems()
        uiStateFlow.value = uiStateFlow.value.copy(likedItems = likedItems)
    }

    init {
        viewModelScope.launch {
            val user = UserSession.currentUser
            val match = repo.getTodayMatchOrNull()


            loadListOfHalls()
            loadLikedItems()

            uiStateFlow.value = uiStateFlow.value.copy(
                username = user?.username ?: "Unknown User",
                selectedHall = match?.matchedHallName ?: ""
            )
        }
    }

}