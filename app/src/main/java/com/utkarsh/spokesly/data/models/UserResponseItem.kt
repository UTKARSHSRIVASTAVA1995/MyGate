package com.utkarsh.spokesly.data.models

data class UserResponseItem(
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
)