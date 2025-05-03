package com.example.hack_challenge_binge_app.viewmodel

import androidx.lifecycle.ViewModel
import com.example.hack_challenge_binge_app.model.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(): ViewModel() {
    val uiEventFlow = MutableStateFlow<UIEvent<Choice>?>(null)

    sealed class Choice {
        object Login : Choice()
        object Signup: Choice()
    }

    fun onLogin() {
        uiEventFlow.value = UIEvent(Choice.Login)
    }

    fun onSignUp() {
        uiEventFlow.value = UIEvent(Choice.Signup)
    }
}