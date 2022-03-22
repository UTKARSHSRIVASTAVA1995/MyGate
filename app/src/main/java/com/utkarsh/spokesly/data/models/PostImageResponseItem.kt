package com.utkarsh.spokesly.data.models

data class PostImageResponseItem(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)