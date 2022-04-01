package com.utkarsh.myGate.di.network.network.interfaces

import com.utkarsh.myGate.data.models.PostCommentsResponse
import retrofit2.Response
import retrofit2.http.GET

interface PostCommentsInterface {

    @GET("comments")
    suspend fun getAllPostComments(): Response<PostCommentsResponse>
}