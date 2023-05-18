package com.example.vkr_map.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    fun signupUser(username: String, password: String, email: String, role: String, onSignupResult: (AuthState) -> Unit) {
        viewModelScope.launch {
            val response = ApiService.authService.signup(username, password, email, role)
            onSignupResult(response)
        }
    }
}