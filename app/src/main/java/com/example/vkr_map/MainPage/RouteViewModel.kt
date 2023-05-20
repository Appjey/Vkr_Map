package com.example.vkr_map.MainPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.vkr_map.Login.User
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch


class RouteViewModel : ViewModel() {
    fun ApplyRoute(valueA: String, valueB: String, navController: NavController, onAddRouteResult: (User) -> Unit = {}) {

        viewModelScope.launch {
            pointA = LatLng(valueA.split("\\s".toRegex()).toTypedArray()[0].toDouble(), valueA.split("\\s".toRegex()).toTypedArray()[1].toDouble())
            pointB = LatLng(valueB.split("\\s".toRegex()).toTypedArray()[0].toDouble(), valueB.split("\\s".toRegex()).toTypedArray()[1].toDouble())
            routeTest = listOf(pointA, pointB)

            navController.navigate("home")
        }
    }
}
