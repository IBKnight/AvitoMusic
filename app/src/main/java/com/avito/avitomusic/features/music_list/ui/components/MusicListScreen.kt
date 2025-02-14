package com.avito.avitomusic.features.music_list.ui.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.avito.avitomusic.features.music_list.ui.MusicListState
import com.avito.avitomusic.features.music_list.ui.viewmodel.ApiMusicListViewModel

@Composable
fun MusicListScreen(
    navController: NavController,
    viewModel: ApiMusicListViewModel = hiltViewModel<ApiMusicListViewModel>()
) {
    // Подписываемся на состояние из ViewModel
    val state by viewModel.state.collectAsState()


    // Загружаем данные при первом запуске
    LaunchedEffect(Unit) {
        viewModel.loadTracks()
    }

    Column {
        var query by remember { mutableStateOf("") }
        Column {
            SearchBar(query, { newValue ->
                query = newValue
            }, onSearch = {
                if (query.isEmpty()) else viewModel.searchTracks(query)

            })
            when (val currentState = state) {
                is MusicListState.Loading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

                is MusicListState.Loaded -> LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items((currentState as MusicListState.Loaded).tracks) { track ->
                        MusicListItem(
                            track = track,
                        )
                    }
                }

                is MusicListState.Error -> Text((currentState as MusicListState.Error).message)
            }
        }


    }

}
