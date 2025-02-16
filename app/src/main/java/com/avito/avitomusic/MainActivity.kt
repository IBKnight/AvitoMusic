package com.avito.avitomusic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.avito.avitomusic.common.components.BottomNavigationBar
import com.avito.avitomusic.common.components.Routes
import com.avito.avitomusic.features.music_list.ui.components.MusicListScreen
import com.avito.avitomusic.features.music_player.ui.components.PlayerScreen
import com.avito.avitomusic.features.music_player.ui.viewmodels.PlayerViewModel
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
                        composable(Routes.SAVED.route) {
                            SavedMusicScreen(
                                context = LocalContext.current,
                                navController = controller
                            )
                        }
                        composable(Routes.SEARCH.route) { MusicListScreen(controller) }
                        composable("${Routes.PLAYER.route}/{trackID}/{artistID}") { backStackEntry ->
                            val trackID =
                                backStackEntry.arguments?.getString("trackID")?.toLongOrNull() ?: 0L
                            val artistID =
                                backStackEntry.arguments?.getString("artistID")?.toLongOrNull()
                                    ?: 0L
                            PlayerScreen(
                                trackID = trackID,
                                artistID = artistID,
                            )
                        }
                    }
                }
            }
        }
    }
}
