package com.utkarsh.spokesly.di.network.service.interfaces

import com.utkarsh.spokesly.data.models.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface UsersApiInterface {

    @GET("users")
    suspend fun getAllUsers(): Response<UserResponse>

}