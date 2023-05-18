package com.example.vkr_map.MainPage

import android.annotation.SuppressLint
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vkr_map.Navigation.Navigation
import com.example.vkr_map.Navigation.NavigationGraph
import com.example.vkr_map.Navigation.currentScreenLabel
import com.example.vkr_map.R
import kotlin.system.exitProcess

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MainPage() {
    val navController = rememberNavController()
    val isLogin = currentScreenLabel == "Login" || currentScreenLabel == "Auth"
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
    val openDialog = remember { mutableStateOf(false) }
    return CenterAlignedTopAppBar(
        title = { Text(text = currentScreenLabel, fontSize = MaterialTheme.typography.headlineLarge.fontSize) },
        actions = {
            Button(onClick = { exitProcess(404) }) {
                Text("LogOut")
            }
        },
        navigationIcon = {
            Button(onClick = { if (currentScreenLabel == "Home") {
                openDialog.value = true
            } }) {
                Text("Left Button")
                if (openDialog.value) {
                    AddRoute(
                        navController = navController,
                        value = "",
                        setShowDialog = { openDialog.value = it },
                        setValue = { }
                    )
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.Black,
        ),
    )
}


@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier, navController: NavController, enabled: Boolean = true) {
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
            NavigationBarItem(
                //                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.title) },
                selected = selected,
                onClick = {
                    if (enabled) {
                        navController.navigate(screen.screen_route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}