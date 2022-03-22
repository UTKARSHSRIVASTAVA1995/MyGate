package com.utkarsh.spokesly.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.utkarsh.spokesly.data.models.PostCommentsResponse
import com.utkarsh.spokesly.data.models.PostImageResponse
import com.utkarsh.spokesly.data.models.UserResponse
import com.utkarsh.spokesly.data.repository.PostCommentsRepository
import com.utkarsh.spokesly.data.repository.UsersRepository
import com.utkarsh.spokesly.di.utils.IResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class PostCommentsViewModel @Inject constructor(
    private val postCommentsRepository: PostCommentsRepository
) : ViewModel() {

    fun fetchPostCommentsFromServer(): LiveData<IResult<PostCommentsResponse>> {
        return postCommentsRepository.fetchPostCommentsFromServer()
            .onStart { emit(IResult.loading()) }
            .asLiveData()
    }

    fun fetchPostPhotosFromServer(): LiveData<IResult<PostImageResponse>> {
        return postCommentsRepository.fetchPostPhotosFromServer()
            .onStart { emit(IResult.loading()) }
            .asLiveData()
    }
}