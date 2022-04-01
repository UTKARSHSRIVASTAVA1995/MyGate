package com.utkarsh.myGate.ui.fragments.userList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.utkarsh.myGate.data.models.UserResponse
import com.utkarsh.myGate.data.repository.UsersRepository
import com.utkarsh.myGate.di.utils.IResult
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