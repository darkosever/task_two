package com.example.task_two.ui

import FullScreenPhoto
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.task_two.model.Photo
import com.example.task_two.viewmodel.AlbumViewModel

@Composable
fun PhotosScreen(viewModel: AlbumViewModel) {
    var selectedPhotoUrl by remember { mutableStateOf<String?>(null) }

    val displayedPhotos = List(1) {
        Photo(
            id = it,
            albumId = 0,
            title = "Dummy Photo",
            url = "https://placehold.co/600.png",
            thumbnailUrl = "https://placehold.co/150.png"
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentPadding = PaddingValues(4.dp)
        ) {
            items(displayedPhotos) { photo ->
                Image(
                    painter = rememberAsyncImagePainter(photo.url),
                    contentDescription = photo.title,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clickable { selectedPhotoUrl = photo.url },
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            }
        }

        selectedPhotoUrl?.let { url ->
            FullScreenPhoto(photoUrl = url, onClose = { selectedPhotoUrl = null })
        }
    }
}