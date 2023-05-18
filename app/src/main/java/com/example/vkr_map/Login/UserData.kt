package com.example.vkr_map.Login

data class User(
    val id: Int,
    val username: String,
    val email: String
)

data class AuthState(
    val message: String,
    val error: String,
)

var activeUser: List<String> = listOf("id", "user", "email")
