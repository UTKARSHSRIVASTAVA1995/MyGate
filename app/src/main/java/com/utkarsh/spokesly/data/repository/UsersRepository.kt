package com.utkarsh.spokesly.data.repository

import android.util.Log
import com.google.gson.Gson
import com.utkarsh.spokesly.data.models.UserResponse
import com.utkarsh.spokesly.di.network.service.api.UsersApi
import com.utkarsh.spokesly.di.utils.IResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val api: UsersApi,
) {

    fun fetchUsersFromServer(): Flow<IResult<UserResponse>> {
        return flow {
            emit(fetchUsers())
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun fetchUsers(): IResult<UserResponse> {
        val result = api.fetchUsers()

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