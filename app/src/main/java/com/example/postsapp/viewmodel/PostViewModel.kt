package com.example.postsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postsapp.model.Post
import com.example.postsapp.repository.PostRepository
import com.example.postsapp.utils.NetworkStatus
import kotlinx.coroutines.launch
import java.lang.Exception

class PostViewModel: ViewModel() {

    private val _status = MutableLiveData<NetworkStatus>()
    val status: LiveData<NetworkStatus> = _status

    private val _postList = MutableLiveData<List<Post>>()
    val postList: LiveData<List<Post>> = _postList

    fun getPosts() = viewModelScope.launch {
        _status.value = NetworkStatus.Loading
        try {
            val response = PostRepository.getPosts()
            _postList.postValue(response.body()?.data)
            _status.value = NetworkStatus.Success
        } catch (e: Exception) {
            _status.value = NetworkStatus.Error
        }

    }
}