package com.example.vkr_map.Login

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {
    @POST("/api/login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String,
    ): User
}

interface AuthService {
    @POST("/api/signup")
    suspend fun signup(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("email") email: String,
        @Query("role") role: String
    ): AuthState
}

object ApiService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://195.135.254.122:5433")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val loginService: LoginService = retrofit.create(LoginService::class.java)
    val authService: AuthService = retrofit.create(AuthService::class.java)
}