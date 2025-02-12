package com.avito.avitomusic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.avito.avitomusic.common.components.BottomNavigationBar
import com.avito.avitomusic.common.components.Routes
import com.avito.avitomusic.ui.theme.AvitoMusicTheme

class App : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val controller = rememberNavController()

            // Получение локализованной строки
            AvitoMusicTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(), bottomBar = {
                        BottomNavigationBar(
                            navController = controller
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = controller,
                        startDestination = Routes.SAVED.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Routes.SAVED.route) { SavedScreen(controller) }
                        composable(Routes.SEARCH.route) { SearchScreen(controller) }
                        composable(Routes.PLAYER.route) { PlayerScreen(controller) }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchScreen(navController: NavController) {
    // Теперь у вас есть доступ к navController
    Button(onClick = { navController.navigate(Routes.PLAYER.route) }) {
        Text("Go to Details")
    }
}

@Composable
fun SavedScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(text = "Saved Screen", fontSize = 24.sp)
            Button(onClick = { navController.navigate(Routes.PLAYER.route) }) {
                Text("Go to Details")
            }
        }

    }
}

@Composable
fun PlayerScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Player Screen", fontSize = 24.sp)
    }
}