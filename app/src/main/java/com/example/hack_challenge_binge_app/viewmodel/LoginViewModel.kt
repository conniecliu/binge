package com.example.hack_challenge_binge_app.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import com.example.hack_challenge_binge_app.model.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    val uiStateFlow: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiEventFlow = MutableStateFlow<UIEvent<LoginSuccess>?>(null)

    data class UiState(
        val username: String = "",
        val password: String = ""
    )

    sealed class LoginSuccess {
        object Success : LoginSuccess()
        data class Error(val message: String) : LoginSuccess()
    }

    fun updateUsername(updatedText: String) {
        uiStateFlow.value = uiStateFlow.value.copy(username = updatedText)
    }

    fun updatePassword(updatedText: String) {
        uiStateFlow.value = uiStateFlow.value.copy(password = updatedText)
    }

    fun onLogin() {
        if (uiStateFlow.value.username.isNotBlank() && uiStateFlow.value.password.isNotBlank()) {
            uiEventFlow.value = UIEvent(LoginSuccess.Success)
        } else {
            uiEventFlow.value = UIEvent(LoginSuccess.Error("Failure to login: please fill out both fields."))
        }
    }

}