package com.utkarsh.myGate.ui.fragments.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.utkarsh.myGate.data.models.PostCommentsResponse

import com.utkarsh.myGate.data.repository.PostCommentsRepository
import com.utkarsh.myGate.di.utils.IResult
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
}