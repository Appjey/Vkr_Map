package com.example.vkr_map.MainPage

import android.location.Location
import com.google.android.gms.maps.model.LatLng

var pointA = LatLng(55.75, 37.61)
var pointB = LatLng(53.9, 27.5667)

var routeTest: List<LatLng> = listOf(LatLng(55.75, 37.61), LatLng(53.9, 27.5667))

fun calculateDistance(
    PointA: LatLng,
    PointB: LatLng,
): Float {
    val startPoint = Location("")
    startPoint.latitude = PointA.latitude
    startPoint.longitude = PointA.longitude

    val endPoint = Location("")
    endPoint.latitude = PointB.latitude
    endPoint.longitude = PointB.longitude

    return Math.round(startPoint.distanceTo(endPoint) / 1000).toFloat()
}