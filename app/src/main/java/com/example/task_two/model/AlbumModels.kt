package com.example.task_two.model

data class Album(
    val userId: Int,
    val id: Int,
    val title: String
)

data class Photo(
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String = "https://via.placeholder.com/600x600/green/lightgreen",
    val thumbnailUrl: String = "https://via.placeholder.com/150x150/green/lightgreen"
)
