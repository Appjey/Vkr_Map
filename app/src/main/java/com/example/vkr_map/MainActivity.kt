package com.example.vkr_map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.vkr_map.MainPage.CheckLocationPermission
import com.example.vkr_map.MainPage.MainPage
import com.example.vkr_map.Settings.isDarkTheme
import com.example.vkr_map.ui.theme.Vkr_MapTheme
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Vkr_MapTheme(darkTheme = isDarkTheme.value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var isPermissionGranted by remember { mutableStateOf(false) }
                    CheckLocationPermission { isGranted ->
                        isPermissionGranted = isGranted
                    }
                    if (ContextCompat.checkSelfPermission(
                            LocalContext.current, Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED) {
                        MainPage()
                    } else {
                        Text(text = "Permission denied")
                        Button(onClick = { exitProcess(404) }) {
                            Text(text = "Refresh")
                        }
                    }
                }
            }
        }
    }
}
