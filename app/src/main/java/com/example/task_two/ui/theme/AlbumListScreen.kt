package com.example.task_two.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.task_two.model.Album
import com.example.task_two.viewmodel.AlbumViewModel
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment

@Composable
fun AlbumListScreen(
    viewModel: AlbumViewModel,
    onAlbumClick: (Album) -> Unit,
    padding: PaddingValues = PaddingValues()
) {
    val albums = viewModel.albums.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()

    if (isLoading.value) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    } else {
        LazyColumn(modifier = Modifier.padding(padding)) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Photo Albums",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            items(albums.value) { album ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .background(
                            color = Color(0xFFF5F5F5),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color(0xFFCCCCCC),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { onAlbumClick(album) }
                        .padding(16.dp)
                ) {
                    Text(
                        text = album.title,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                }
            }
        }
    }
}