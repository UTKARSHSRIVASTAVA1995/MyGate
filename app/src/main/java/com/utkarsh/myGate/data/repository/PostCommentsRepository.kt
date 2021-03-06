package com.utkarsh.myGate.data.repository

import com.utkarsh.myGate.data.models.PostCommentsResponse
import com.utkarsh.myGate.di.network.network.api.PostCommentsApi
import com.utkarsh.myGate.di.utils.IResult
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
}