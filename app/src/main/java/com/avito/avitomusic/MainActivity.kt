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
import com.avito.avitomusic.features.music_list.domain.models.AlbumModel
import com.avito.avitomusic.features.music_list.domain.models.ArtistModel
import com.avito.avitomusic.features.music_list.domain.models.TrackModel
import com.avito.avitomusic.features.music_list.ui.components.MusicListItem
import com.avito.avitomusic.features.music_list.ui.components.MusicListScreen
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
                        composable(Routes.SAVED.route) { SavedScreen(controller) }
                        composable(Routes.SEARCH.route) { MusicListScreen(controller) }
                        composable(Routes.PLAYER.route) { PlayerScreen(controller) }
                    }
                }
            }
        }
    }
}


val artist = ArtistModel(
    id = 27,
    name = "Eminem",
    link = "https://www.deezer.com/artist/27",
    picture = "https://api.deezer.com/artist/27/image",
    pictureSmall = "https://e-cdns-images.dzcdn.net/images/artist/100x100-000000-80-0-0.jpg",
    pictureMedium = "https://e-cdns-images.dzcdn.net/images/artist/250x250-000000-80-0-0.jpg",
    pictureBig = "https://e-cdns-images.dzcdn.net/images/artist/500x500-000000-80-0-0.jpg",
    pictureXl = "https://e-cdns-images.dzcdn.net/images/artist/1000x1000-000000-80-0-0.jpg",
    radio = true,
    tracklist = "https://api.deezer.com/artist/27/top?limit=50",
    type = "artist"
)

val album = AlbumModel(
    id = 302127,
    title = "The Eminem Show",
    cover = "https://api.deezer.com/album/302127/image",
    coverSmall = "https://e-cdns-images.dzcdn.net/images/cover/56x56-000000-80-0-0.jpg",
    coverMedium = "https://e-cdns-images.dzcdn.net/images/cover/250x250-000000-80-0-0.jpg",
    coverBig = "https://e-cdns-images.dzcdn.net/images/cover/500x500-000000-80-0-0.jpg",
    coverXl = "https://e-cdns-images.dzcdn.net/images/cover/1000x1000-000000-80-0-0.jpg",
    md5Image = "f2f2f2f2f2f2f2f2f2f2f2f2f2f2f2f2",
    tracklist = "https://api.deezer.com/album/302127/tracks",
    type = "album"
)

val track = TrackModel(
    id = 1109731,
    title = "Without Me",
    titleShort = "Without Me",
    link = "https://www.deezer.com/track/1109731",
    duration = 290,
    rank = 987654,
    explicitLyrics = true,
    explicitContentLyrics = 1,
    explicitContentCover = 0,
    preview = "https://cdns-preview.dzcdn.net/stream/1234567890.mp3",
    md5Image = "a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6",
    position = 1,
    artist = artist,
    album = album,
    type = "track"
)

@Composable
fun SavedScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            MusicListItem(track)
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