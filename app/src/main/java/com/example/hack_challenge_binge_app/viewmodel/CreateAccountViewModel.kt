package com.example.hack_challenge_binge_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hack_challenge_binge_app.model.RegisterRequest
import com.example.hack_challenge_binge_app.model.UIEvent
import com.example.hack_challenge_binge_app.retrofit.RetrofitInstance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val retrofitInstance: RetrofitInstance
):ViewModel(
) {
    val uiStateFlow: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiEventFlow = MutableStateFlow<UIEvent<CreateSuccess>?>(null)

    data class UiState(
        val email: String = "",
        val username: String = "",
        val password: String = "",
        val confirmPass: String = "",
        val name: String = ""
    )

    sealed class CreateSuccess {
        object Success : CreateSuccess()
        data class Error(val message: String) : CreateSuccess()
    }

    fun updateEmail(updatedText: String) {
        uiStateFlow.value = uiStateFlow.value.copy(email = updatedText)
    }

    fun updateUsername(updatedText: String) {
        uiStateFlow.value = uiStateFlow.value.copy(username = updatedText)
    }

    fun updatePassword(updatedText: String) {
        uiStateFlow.value = uiStateFlow.value.copy(password = updatedText)
    }

    fun updateConfirmPass(updatedText: String) {
        uiStateFlow.value = uiStateFlow.value.copy(confirmPass = updatedText)
    }

    fun updateName(updatedText: String) {
        uiStateFlow.value = uiStateFlow.value.copy(name = updatedText)
    }

    fun onCreate() {
        val state = uiStateFlow.value

        if (state.email.isBlank() || state.username.isBlank() || state.password.isBlank() || state.confirmPass.isBlank()) {
            uiEventFlow.value = UIEvent(CreateSuccess.Error("Please fill out all fields."))
            return
        }

        if (state.password != state.confirmPass) {
            uiEventFlow.value = UIEvent(CreateSuccess.Error("Passwords do not match."))
            return
        }

        // registering the user
        viewModelScope.launch {
            try {
                val response = retrofitInstance.apiService.registerUser(
                    RegisterRequest(
                        username = state.username,
                        email = state.email,
                        password = state.password
                    )
                )
                uiEventFlow.value = UIEvent(CreateSuccess.Success)
            } catch (e: Exception) {
                uiEventFlow.value = UIEvent(CreateSuccess.Error(e.localizedMessage ?: "Network error"))
            }
        }
    }
}