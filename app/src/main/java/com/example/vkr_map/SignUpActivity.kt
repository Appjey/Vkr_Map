package com.example.vkr_map
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.vkr_map.Login.AuthScreen
import com.example.vkr_map.Login.AuthViewModel
import com.example.vkr_map.ui.theme.Vkr_MapTheme


class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Vkr_MapTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AuthScreen(AuthViewModel()) {
                        Toast.makeText(this, "Successfully Registered!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                }
            }
        }
    }
}