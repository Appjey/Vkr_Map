package com.example.vkr_map.Navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vkr_map.MainPage.MapFragment
import com.example.vkr_map.Profile.ProfileFragment
import com.example.vkr_map.Settings.SettingsFragment

sealed class Navigation(var title:String, var icon: ImageVector, var screen_route:String) {
    object Profile : Navigation("Profile", Icons.Filled.Person, "Profile")
    object Home : Navigation("Home", Icons.Filled.Home, "Home")
    object Settings : Navigation("Settings", Icons.Filled.Settings, "Settings")

}
var currentScreenLabel = Navigation.Home.screen_route

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Navigation.Home.screen_route) {
        composable(Navigation.Profile.screen_route) {
            ProfileFragment().ProfileView()
            currentScreenLabel = Navigation.Profile.screen_route
        }
        composable(Navigation.Home.screen_route) {
            MapFragment().MapView()
            currentScreenLabel = Navigation.Home.screen_route
        }
        composable(Navigation.Settings.screen_route) {
            SettingsFragment().SettingsView()
            currentScreenLabel = Navigation.Settings.screen_route
        }
    }
}