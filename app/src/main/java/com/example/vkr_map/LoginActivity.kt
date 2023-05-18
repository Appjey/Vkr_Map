package com.example.vkr_map
import LoginScreen
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vkr_map.Login.LoginViewModel
import com.example.vkr_map.ui.theme.Vkr_MapTheme


class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loginViewModel = viewModel<LoginViewModel>()

            Vkr_MapTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    LoginScreen(loginViewModel) {
                        Toast.makeText(this, "Successfully logged in!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                }
            }
        }
    }
}