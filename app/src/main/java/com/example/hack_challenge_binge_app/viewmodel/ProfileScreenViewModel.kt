package com.example.hack_challenge_binge_app.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import com.example.hack_challenge_binge_app.model.FoodItem
import com.example.hack_challenge_binge_app.model.UIEvent
import com.example.hack_challenge_binge_app.viewmodel.LoginViewModel.LoginSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor() : ViewModel() {
    val uiStateFlow: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiEventFlow = MutableStateFlow<UIEvent<Unit>?>(null)

    data class UiState(
        val username: String = "Unknown User",
        val userProfile: ImageBitmap? = null,
        val selectedHall: String = "",
        val allHalls: List<String> = listOf("North Star", "Risley", "Bethe Jansen"),
        val likedItems: List<FoodItem> = listOf()
    )

}