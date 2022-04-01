package com.utkarsh.myGate.di.network.network.interfaces

import com.utkarsh.myGate.data.models.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface UsersApiInterface {

    @GET("users")
    suspend fun getAllUsers(): Response<UserResponse>

}