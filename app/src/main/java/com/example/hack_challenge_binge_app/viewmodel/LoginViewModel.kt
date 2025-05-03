package com.example.hack_challenge_binge_app.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hack_challenge_binge_app.model.LoginRequest
import com.example.hack_challenge_binge_app.model.UIEvent
import com.example.hack_challenge_binge_app.model.UserSession
import com.example.hack_challenge_binge_app.retrofit.RetrofitInstance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val retrofitInstance: RetrofitInstance
) : ViewModel() {
    val uiStateFlow: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiEventFlow = MutableStateFlow<UIEvent<LoginSuccess>?>(null)

    data class UiState(
        val email: String = "",
        val password: String = ""
    )

    sealed class LoginSuccess {
        object Success : LoginSuccess()
        data class Error(val message: String) : LoginSuccess()
    }

    fun updateEmail(updatedText: String) {
        uiStateFlow.value = uiStateFlow.value.copy(email = updatedText)
    }

    fun updatePassword(updatedText: String) {
        uiStateFlow.value = uiStateFlow.value.copy(password = updatedText)
    }

    fun onLogin() {
        val state = uiStateFlow.value

        if (state.email.isBlank() || state.password.isBlank()) {
            uiEventFlow.value = UIEvent(LoginSuccess.Error("Please fill out both fields."))
            return
        }

        viewModelScope.launch {
            try {
                val response = retrofitInstance.apiService.loginUser(
                    LoginRequest(email = state.email, password = state.password)
                )
                uiEventFlow.value = UIEvent(LoginSuccess.Success)
                UserSession.currentUser = response
            } catch (e: Exception) {
                uiEventFlow.value = UIEvent(LoginSuccess.Error("Login failed: ${e.localizedMessage}"))
            }
        }
    }

}