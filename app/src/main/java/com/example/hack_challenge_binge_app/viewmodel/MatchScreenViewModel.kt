package com.example.hack_challenge_binge_app.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hack_challenge_binge_app.R
import com.example.hack_challenge_binge_app.model.DiningRepo
import com.example.hack_challenge_binge_app.model.MatchResultDummy
import com.example.hack_challenge_binge_app.model.UIEvent
import com.example.hack_challenge_binge_app.viewmodel.LoginViewModel.LoginSuccess
import com.example.hack_challenge_binge_app.viewmodel.LoginViewModel.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MatchScreenViewModel @Inject constructor(
    private val repo: DiningRepo
) : ViewModel() {
    val uiStateFlow: MutableStateFlow<MatchScreenViewModel.UiState> = MutableStateFlow(UiState())
    val uiEventFlow = MutableStateFlow<UIEvent<UserChoice>?>(null)

    data class UiState(
        val matchedHallName: String? = null,
        val matchedImage: ImageBitmap? = null,
        val userProfile: ImageBitmap? = null,
        val loading: Boolean = true,
        val hasMatchedToday: Boolean = false
    )

    sealed class UserChoice {
        object Continue : UserChoice()
        object Rematch : UserChoice()
    }

    fun onRematch() {
        uiStateFlow.value = uiStateFlow.value.copy(
            matchedHallName = null,
            matchedImage = null
        )
        uiEventFlow.value = UIEvent(UserChoice.Rematch)
    }

    fun onContinue() {
        uiEventFlow.value = UIEvent(UserChoice.Continue)
    }

    fun loadMatchedResult(diningHallName: String) {
        viewModelScope.launch {
            val matchedBitmap = repo.getImageForDiningHall(diningHallName)
            uiStateFlow.value = uiStateFlow.value.copy(
                matchedHallName = diningHallName,
                matchedImage = matchedBitmap,
                loading = false,
                hasMatchedToday = true
            )
        }
    }

    fun setUserProfileImage(bitmap: ImageBitmap) {
        uiStateFlow.value = uiStateFlow.value.copy(userProfile = bitmap)
    }

    fun checkIfUserHasMatchToday() {
        viewModelScope.launch {
            uiStateFlow.value = uiStateFlow.value.copy(loading = true)

            val match = repo.getTodayMatchOrNull()
            if (match != null) {
                loadMatchedResult(match.matchedHallName.toString())
            } else {
                uiStateFlow.value = uiStateFlow.value.copy(
                    matchedHallName = null,
                    matchedImage = null,
                    hasMatchedToday = false,
                    loading = false
                )
            }
        }
    }

    init {
        viewModelScope.launch {
            val name = MatchResultDummy.matchedHallName
            val image = MatchResultDummy.matchedImage

            uiStateFlow.value = UiState(
                matchedHallName = name,
                matchedImage = image,
            )
        }
    }

}