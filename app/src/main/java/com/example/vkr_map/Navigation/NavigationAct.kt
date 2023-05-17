package com.example.vkr_map.Navigation

import LoginScreen
import LoginScreenPreview
import android.media.Image
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.navigation.compose.rememberNavController
import com.example.vkr_map.MainPage
import com.example.vkr_map.MainPage.MapFragment
import com.example.vkr_map.Profile.ProfileFragment
import kotlin.system.exitProcess

sealed class Navigation(var title:String, var icon: ImageVector, var screen_route:String) {
    object Profile : Navigation("Profile", Icons.Filled.Person, "Profile")
    object Home : Navigation("Home", Icons.Filled.Home, "Home")
    object Settings : Navigation("Settings", Icons.Filled.Settings, "Settings")
    object Login : Navigation("Login", Icons.Filled.Settings, "Login")
//    object Music : NavigationItem("music", R.drawable.ic_music, "Music")
//    object Movies : NavigationItem("movies", R.drawable.ic_movie, "Movies")
//    object Books : NavigationItem("books", R.drawable.ic_book, "Books")
}
var currentScreenLabel = Navigation.Home.screen_route
@Composable
fun NavigationController(Destination: String) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Home") {
//        composable("Settings") { SettingsFagment(/*...*/) }
        //composable("friendslist") { FriendsList(/*...*/) }
    }
    navController.navigate("Settings") {
        popUpTo("Home") { inclusive = true }
    }
}

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
//        composable(Navigation.Settings.screen_route) {
//            SettingsFagment().SettingsScreen()
//        }
        composable(Navigation.Login.screen_route) {
            LoginScreenPreview()
            currentScreenLabel = Navigation.Login.screen_route
        }
//        composable(NavigationItem.Jobs.screen_route) {
//            JobScreen()
//        }
    }
}