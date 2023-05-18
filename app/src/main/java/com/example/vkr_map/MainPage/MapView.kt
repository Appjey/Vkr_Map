package com.example.vkr_map.MainPage

import android.annotation.SuppressLint
import android.util.LruCache
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.vkr_map.BottomNavigationBar
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


val cacheSize = 10 * 1024 * 1024 // 10 MiB
val polylineCache = LruCache<String, List<LatLng>>(cacheSize)
var routeTest: List<LatLng> = listOf(LatLng(55.75, 37.61), LatLng(53.9, 27.5667))
class MapFragment : Fragment() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun MapView(
    ) {
        MapScreen()
    }



    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable()
    fun MapScreen() {
        val navController = rememberNavController()
        val moscow = LatLng(55.75, 37.61)
        val minsk = LatLng(53.9, 27.5667)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(moscow, 10f)
        }
        var uiSettings by remember { mutableStateOf(MapUiSettings()) }
        val mapLoaded = remember { mutableStateOf(false) }
        var properties by remember {
            mutableStateOf(
                MapProperties(
                    mapType = MapType.NORMAL,
                    isMyLocationEnabled = true,
                    isBuildingEnabled = true
                )
            )
        }
        Scaffold(
            topBar = { MapTopAppBar(title = "Main Page", navController = navController) },
            bottomBar = {
                BottomNavigationBar(modifier = Modifier, navController = navController)
            },
            content = {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val mapKey = remember { mutableStateOf(0) }
                    GoogleMap(
                        properties = properties,
                        uiSettings = uiSettings,
                        cameraPositionState = cameraPositionState,
                        onMapLoaded = {
                            mapLoaded.value = true
                        }
                    ) {
                        Polyline(points = routeTest, color = Color.Red, width = 10f)

                        }
                    DisposableEffect(Unit) {
                        // Переотрисовка карты при изменении нового пути
                        mapKey.value += 1
                        onDispose { }
                    }
                }
            }
        )

        LaunchedEffect(mapLoaded.value) {
            if (mapLoaded.value) {

                mapLoaded.value = false
            }
        }
    }

    // Классы для разбора ответа от Google Directions API
//    data class DirectionsResponse(val routes: List<Route>)
//    data class Route(val legs: List<Leg>)
//    data class Leg(val steps: List<Step>)
//    data class Step(val polyline: Polyline)
//    data class Polyline(val points: PolylineOptions)

//    suspend fun loadMapData(id: String): PolylineOptions {
//        // Проверяем, есть ли данные в кэше
//        val cached = polylineCache.get(id)
//        if (cached != null) {
//            return cached
//        }
//
//        // Если данных нет в кэше, загружаем их
//        val data =  // загружаемые данные
//            withContext(Dispatchers.IO) {  // загрузка в фоновом потоке
//                PolylineOptions().add(
//                    LatLng(55.75, 37.61),
//                    LatLng(55.75, 37.61),
//                    LatLng(55.75, 37.61)
//                )
//            }
//
//        // Сохраняем данные в кэше перед возвратом
//        polylineCache.put(id, data)
//        return data
//    }


    suspend fun getMarkers() {
    }

    suspend fun getRoute() {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapTopAppBar(title: String, navController: NavController) {
    CenterAlignedTopAppBar(
        title = { Text(text = title, fontSize = MaterialTheme.typography.headlineLarge.fontSize) },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.Black,
        ),
    )
}
