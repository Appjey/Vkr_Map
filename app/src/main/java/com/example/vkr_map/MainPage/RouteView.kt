package com.example.vkr_map.MainPage

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions


class RouteViewModel {
    fun addRoute(valueA: String, valueB: String) {

        val pointA = LatLng(valueA.split("\\s".toRegex()).toTypedArray()[0].toDouble(), valueA.split("\\s".toRegex()).toTypedArray()[1].toDouble())
        val pointB = LatLng(valueB.split("\\s".toRegex()).toTypedArray()[0].toDouble(), valueB.split("\\s".toRegex()).toTypedArray()[1].toDouble())
        routeTest = listOf(pointA, pointB)
//        MapFragment.MapScreen.mapView.invalidate()
    }
}