//package com.example.vkr_map.MainPage
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.DisposableEffect
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.viewinterop.AndroidView
//import ru.dublgis.dgismobile.mapsdk.MapFragment
//import ru.dublgis.dgismobile.mapsdk.LonLat
//
//@Composable
//fun MapViewComposable(
//    apiKey: String,
//    center: LonLat,
//    zoom: Double,
//    modifier: Modifier = Modifier
//) {
//    val mapFragment = MapFragment.newInstance(apiKey)
//    val bundle = Bundle().apply {
//        putDoubleArray("center", doubleArrayOf(center.lon, center.lat))
//        putDouble("zoom", zoom)
//    }
//
//    mapFragment.arguments = bundle
//
//    AndroidView(
//        factory = { context ->
//            mapFragment.onCreateView(LayoutInflater.from(context), null, bundle).apply {
//                id = R.id.mapFragment
//            }
//        },
//        modifier = modifier,
//        update = { mapView ->
//            // You can update the mapView here if needed
//        }
//    )
//
//    DisposableEffect(Unit) {
//        onDispose {
//            mapFragment.onDestroyView()
//        }
//    }
//}
