package com.avito.avitomusic

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.avito.avitomusic.common.components.BottomNavigationBar
import com.avito.avitomusic.common.components.Routes
import com.avito.avitomusic.features.music_list.domain.models.AlbumModel
import com.avito.avitomusic.features.music_list.domain.models.ArtistModel
import com.avito.avitomusic.features.music_list.domain.models.TrackModel
import com.avito.avitomusic.features.music_list.ui.components.MusicListItem
import com.avito.avitomusic.features.music_list.ui.components.MusicListScreen
import com.avito.avitomusic.features.saved_music_list.ui.components.SavedMusicScreen
import com.avito.avitomusic.ui.theme.AvitoMusicTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()



        setContent {
            val controller = rememberNavController()
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
                        startDestination = Routes.SEARCH.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Routes.SAVED.route) { SavedMusicScreen(context = LocalContext.current) }
                        composable(Routes.SEARCH.route) { MusicListScreen(controller) }
                        composable(Routes.PLAYER.route) { PlayerScreen(controller) }
                    }
                }
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