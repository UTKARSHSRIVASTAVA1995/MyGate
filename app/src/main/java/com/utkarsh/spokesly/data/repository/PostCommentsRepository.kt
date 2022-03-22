package com.utkarsh.spokesly.data.repository

import android.util.Log
import com.google.gson.Gson
import com.utkarsh.spokesly.data.models.PostCommentsResponse
import com.utkarsh.spokesly.data.models.PostImageResponse
import com.utkarsh.spokesly.data.models.UserResponse
import com.utkarsh.spokesly.di.network.service.api.PostCommentsApi
import com.utkarsh.spokesly.di.network.service.api.UsersApi
import com.utkarsh.spokesly.di.utils.IResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostCommentsRepository @Inject constructor(
    private val api: PostCommentsApi
) {

    fun fetchPostCommentsFromServer(): Flow<IResult<PostCommentsResponse>> {
        return flow {
            emit(fetchPostComments())
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun fetchPostComments(): IResult<PostCommentsResponse> {
        val result = api.fetchPostComments()

        when (result.status) {
            IResult.Status.SUCCESS -> {
                if (result.data != null) {
                    withContext(Dispatchers.IO) {
                    }
                }
                IResult.success(result.data)
            }
            IResult.Status.ERROR -> {
            }
        }
        return result
    }

    fun fetchPostPhotosFromServer(): Flow<IResult<PostImageResponse>> {
        return flow {
            emit(fetchPostPhotos())
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun fetchPostPhotos(): IResult<PostImageResponse> {
        val result = api.fetchPostPhotos()

        when (result.status) {
            IResult.Status.SUCCESS -> {
                if (result.data != null) {
                    withContext(Dispatchers.IO) {
                    }
                }
                IResult.success(result.data)
            }
            IResult.Status.ERROR -> {
            }
        }
        return result
    }
}