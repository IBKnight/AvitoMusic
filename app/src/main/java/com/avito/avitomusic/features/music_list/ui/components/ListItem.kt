package com.avito.avitomusic.features.music_list.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.avito.avitomusic.features.music_list.domain.models.TrackModel

@Composable
fun MusicListItem(track: TrackModel, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .height(70.dp)
            .fillMaxSize()
            .clickable {

            }
    ) {
        Row {
            Box(
                Modifier
                    .fillMaxHeight()
                    .width(70.dp)

                    .clip(RoundedCornerShape(8.dp))
            )
            {
                AsyncImage(
                    track.artist.picture,
                    contentDescription = null,
                    modifier = Modifier.size(70.dp)
                )
            }
            Spacer(modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    track.title, fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Spacer(modifier.height(1.dp))
                Text(
                    track.artist.name,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 14.sp
                )
            }
        }
    }


}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    AvitoMusicTheme {
//        MusicListItem(track)
//    }
//}
