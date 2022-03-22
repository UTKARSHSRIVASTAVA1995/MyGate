package com.utkarsh.spokesly.data.models

data class PostCommentsResponseItem(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
)