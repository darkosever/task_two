package com.example.task_two

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.task_two.model.Album
import com.example.task_two.ui.PhotosScreen
import com.example.task_two.ui.theme.AlbumListScreen
import com.example.task_two.ui.theme.Task_twoTheme
import com.example.task_two.viewmodel.AlbumViewModel
import androidx.compose.ui.Alignment

class MainActivity : ComponentActivity() {
    private val viewModel = AlbumViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Task_twoTheme {
                var selectedAlbum by remember { mutableStateOf<Album?>(null) }

                LaunchedEffect(Unit) {
                    viewModel.fetchAlbums()
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        if (selectedAlbum == null) {
                            AlbumListScreen(
                                viewModel = viewModel,
                                onAlbumClick = { album ->
                                    selectedAlbum = album
                                },
                                padding = innerPadding
                            )
                        } else {
                            LaunchedEffect(selectedAlbum) {
                                viewModel.fetchPhotos(selectedAlbum!!.id)
                            }

                            PhotosScreen(viewModel = viewModel)

                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                Button(onClick = { selectedAlbum = null }) {
                                    Text("Back to Albums")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
