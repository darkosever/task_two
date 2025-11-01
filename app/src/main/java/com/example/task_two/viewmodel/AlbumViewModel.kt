package com.example.task_two.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task_two.model.Album
import com.example.task_two.model.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Retrofit API
interface AlbumApiService {
    @GET("albums")
    suspend fun getAlbums(): List<Album>

    @GET("photos")
    suspend fun getPhotos(@Query("albumId") albumId: Int): List<Photo>
}

object AlbumService {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val api: AlbumApiService = retrofit.create(AlbumApiService::class.java)
}

class AlbumViewModel : ViewModel() {

    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums = _albums.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _photos = MutableStateFlow<List<Photo>>(emptyList())
    val photos = _photos.asStateFlow()

    private val _isLoadingPhotos = MutableStateFlow(false)
    val isLoadingPhotos = _isLoadingPhotos.asStateFlow()

    fun fetchPhotos(albumId: Int) {
        viewModelScope.launch {
            _isLoadingPhotos.value = true
            try {
                val fetchedPhotos = AlbumService.api.getPhotos(albumId)
                _photos.value = fetchedPhotos.map { photo ->
                    photo.copy(
                        url = photo.url.ifEmpty { "https://placehold.co/600x600/green/lightgreen" },
                        thumbnailUrl = photo.thumbnailUrl.ifEmpty { "https://placehold.co/150x150/green/lightgreen" }
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _photos.value = List(20) { index ->
                    Photo(
                        id = index,
                        albumId = albumId,
                        title = "Placeholder $index"
                    )
                }
            } finally {
                _isLoadingPhotos.value = false
            }
        }
    }


    fun fetchAlbums() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _albums.value = AlbumService.api.getAlbums()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}