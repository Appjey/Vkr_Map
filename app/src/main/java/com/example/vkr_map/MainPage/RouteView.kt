package com.example.vkr_map.MainPage

import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng


class RouteViewModel {
    fun ApplyRoute(valueA: String, valueB: String, navController: NavController) {

        val pointA = LatLng(valueA.split("\\s".toRegex()).toTypedArray()[0].toDouble(), valueA.split("\\s".toRegex()).toTypedArray()[1].toDouble())
        val pointB = LatLng(valueB.split("\\s".toRegex()).toTypedArray()[0].toDouble(), valueB.split("\\s".toRegex()).toTypedArray()[1].toDouble())
        routeTest = listOf(pointA, pointB)
//        MapFragment.MapScreen.mapView.invalidate()
        navController.navigate("home")
    }
}