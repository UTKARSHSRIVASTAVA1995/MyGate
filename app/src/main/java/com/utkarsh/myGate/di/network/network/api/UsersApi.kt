package com.utkarsh.myGate.di.network.network.api

import com.utkarsh.myGate.data.models.UserResponse
import com.utkarsh.myGate.di.network.network.NetworkService
import com.utkarsh.myGate.di.network.network.interfaces.UsersApiInterface
import com.utkarsh.myGate.di.utils.IResult
import com.utkarsh.myGate.di.utils.handleApiResponse
import javax.inject.Inject

class UsersApi @Inject constructor(
    private val networkService: NetworkService
) {

    suspend fun fetchUsers(): IResult<UserResponse> {
        val userService = networkService.build(UsersApiInterface::class.java)
        return handleApiResponse(request = { userService.getAllUsers() })
    }
}