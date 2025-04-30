package com.example.hack_challenge_binge_app.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import com.example.hack_challenge_binge_app.model.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MatchScreenViewModel @Inject constructor() : ViewModel() {
    private val _uiStateFlow = MutableStateFlow(UiState())
    val uiStateFlow: StateFlow<UiState> = _uiStateFlow

    data class UiState(
        val matchedHallName: String? = null,
        val matchedImage: ImageBitmap? = null,
    )

    fun onMatchFound(name: String, image: ImageBitmap) {
        _uiStateFlow.value = _uiStateFlow.value.copy(
            matchedHallName = name,
            matchedImage = image
        )
    }
}