package com.avito.avitomusic.features.saved_music_list.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
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
import com.avito.avitomusic.features.saved_music_list.domain.models.SavedTracksModel

@Composable
fun SavedMusicListItem(
    track: SavedTracksModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .height(70.dp)
            .fillMaxSize()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Обложка трека
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                AsyncImage(
                    model = track.uri,
                    contentDescription = null,
                    modifier = Modifier.size(70.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Название трека и исполнитель
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = track.title,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = track.artist,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Значок "скачено"
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Downloaded",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 8.dp)
            )
        }
    }
}