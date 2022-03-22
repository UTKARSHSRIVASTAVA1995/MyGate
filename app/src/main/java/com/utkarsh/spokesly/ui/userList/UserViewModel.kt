package com.utkarsh.spokesly.ui.userList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.utkarsh.spokesly.data.models.UserResponse
import com.utkarsh.spokesly.data.repository.UsersRepository
import com.utkarsh.spokesly.di.utils.IResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UsersRepository
) : ViewModel() {

    fun fetchUsersFromServer(): LiveData<IResult<UserResponse>> {
        return userRepository.fetchUsersFromServer()
            .onStart { emit(IResult.loading()) }
            .asLiveData()
    }
}