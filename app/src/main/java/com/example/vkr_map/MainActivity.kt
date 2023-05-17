package com.example.vkr_map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vkr_map.MainPage.MapFragment
import com.example.vkr_map.Navigation.Navigation
import com.example.vkr_map.Navigation.NavigationGraph
import com.example.vkr_map.Navigation.currentScreenLabel
import com.example.vkr_map.ui.theme.Vkr_MapTheme
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Vkr_MapTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var isPermissionGranted = remember { false }
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

@Composable
fun CheckLocationPermission(onPermissionResult: (Boolean) -> Unit) {
    val context = LocalContext.current

    // Проверяем разрешение на доступ к местоположению
    val isPermissionGranted = rememberSaveable {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Запускаем запрос разрешения
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        isPermissionGranted.value = isGranted
        onPermissionResult(isGranted)
    }

    LaunchedEffect(key1 = isPermissionGranted.value) {
        if (!isPermissionGranted.value) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            onPermissionResult(true)
        }
    }
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MainPage() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopAppBarWithButtons(navController = navController) },
        bottomBar = { BottomNavigationBar(modifier = Modifier, navController = navController) }
    ) {
//        MainContent(modifier = Modifier)
        NavigationGraph(navController = navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithButtons(navController: NavController) {
    return CenterAlignedTopAppBar(
        title = {Text(text = currentScreenLabel, fontSize = MaterialTheme.typography.headlineLarge.fontSize)},
        actions = {
            Button(onClick = { navController.navigate("Login") }) {
                Text("Login")
            }
        },
        navigationIcon = {
            Button(onClick = { /* Handle left button click */ }) {
                Text("Left Button")
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.Black,
        ),
    )
}


@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier, navController: NavController) {
    val navItems = listOf(

        Navigation.Profile,
        Navigation.Home,
        Navigation.Settings,
    )
    NavigationBar(
        containerColor = colorResource(id = R.color.teal_200),
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination?.route
        val context = LocalContext.current
        navItems.forEach { screen ->
            val selected = currentDestination == screen.screen_route
            val tint by animateColorAsState(
                if (selected) MaterialTheme.colorScheme.primary else Color.Black, label = ""
            )
            NavigationBarItem(
                //                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.title) },
                selected = selected,
                onClick = {
                    navController.navigate(screen.screen_route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Vkr_MapTheme {
        MainPage()
    }
}
