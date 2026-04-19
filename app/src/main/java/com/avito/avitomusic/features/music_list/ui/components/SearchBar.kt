package com.avito.avitomusic.features.music_list.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.avito.avitomusic.R

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = query,
            suffix = {
                if (query.isNotEmpty()) {
                    IconButton(modifier = Modifier.size(24.dp), onClick = { onClear() }) {
                        val icon = Icons.Default.Clear
                        Icon(
                            imageVector = icon,
                            contentDescription = "Clear"
                        )
                    }
                }
            },
            onValueChange = onQueryChange,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(24.dp),
            singleLine = false,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = { Text(stringResource(id = R.string.search_tracks)) }
        )
        Spacer(modifier = Modifier.width(8.dp))
    }
}