package com.example.vkr_map.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    fun loginUser(username: String, password: String, onLoginResult: (User) -> Unit) {

        viewModelScope.launch {
            val response = ApiService.loginService.login(username, password)

            onLoginResult(response)
        }
    }
}