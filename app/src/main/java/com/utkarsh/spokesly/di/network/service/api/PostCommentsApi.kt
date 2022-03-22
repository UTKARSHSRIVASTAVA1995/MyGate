package com.utkarsh.spokesly.di.network.service.api

import com.utkarsh.spokesly.data.models.PostCommentsResponse
import com.utkarsh.spokesly.data.models.PostImageResponse
import com.utkarsh.spokesly.di.network.service.NetworkService
import com.utkarsh.spokesly.di.network.service.interfaces.PostCommentsInterface
import com.utkarsh.spokesly.di.utils.IResult
import com.utkarsh.spokesly.di.utils.handleApiResponse
import javax.inject.Inject

class PostCommentsApi @Inject constructor(
    private val networkService: NetworkService
) {
    suspend fun fetchPostComments(): IResult<PostCommentsResponse> {
        val postCommentsService = networkService.build(PostCommentsInterface::class.java)
        return handleApiResponse(request = { postCommentsService.getAllPostComments() })
    }

    suspend fun fetchPostPhotos(): IResult<PostImageResponse> {
        val postCommentsService = networkService.build(PostCommentsInterface::class.java)
        return handleApiResponse(request = { postCommentsService.getAllPostPhotos() })
    }
}