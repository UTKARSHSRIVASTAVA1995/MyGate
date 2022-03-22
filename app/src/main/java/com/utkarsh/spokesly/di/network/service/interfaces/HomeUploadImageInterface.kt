package com.utkarsh.spokesly.di.network.service.interfaces

import com.utkarsh.spokesly.data.models.PostCommentsResponse
import com.utkarsh.spokesly.data.models.PostImageResponse
import com.utkarsh.spokesly.data.models.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface HomeUploadImageInterface {

    @POST("posts")
    suspend fun uploadImage(): Response<PostCommentsResponse>

}