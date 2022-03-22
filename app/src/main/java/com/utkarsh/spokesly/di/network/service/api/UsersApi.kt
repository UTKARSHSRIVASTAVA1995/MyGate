package com.utkarsh.spokesly.di.network.service.api

import com.utkarsh.spokesly.data.models.UserResponse
import com.utkarsh.spokesly.di.network.service.NetworkService
import com.utkarsh.spokesly.di.network.service.interfaces.UsersApiInterface
import com.utkarsh.spokesly.di.utils.IResult
import com.utkarsh.spokesly.di.utils.handleApiResponse
import javax.inject.Inject

class UsersApi @Inject constructor(
    private val networkService: NetworkService
) {

    suspend fun fetchUsers(): IResult<UserResponse> {
        val userService = networkService.build(UsersApiInterface::class.java)
        return handleApiResponse(request = { userService.getAllUsers() })
    }
}