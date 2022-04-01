package com.utkarsh.myGate.di.network.network.api

import com.utkarsh.myGate.data.models.PostCommentsResponse
import com.utkarsh.myGate.di.network.network.NetworkService
import com.utkarsh.myGate.di.network.network.interfaces.PostCommentsInterface
import com.utkarsh.myGate.di.utils.IResult
import com.utkarsh.myGate.di.utils.handleApiResponse
import javax.inject.Inject

class PostCommentsApi @Inject constructor(
    private val networkService: NetworkService
) {
    suspend fun fetchPostComments(): IResult<PostCommentsResponse> {
        val postCommentsService = networkService.build(PostCommentsInterface::class.java)
        return handleApiResponse(request = { postCommentsService.getAllPostComments() })
    }
}