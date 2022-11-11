package com.nextgen.mygithubuserapp.ui.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.mygithubuserapp.data.remote.response.UserResponse
import com.nextgen.mygithubuserapp.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.*
import java.lang.Exception

class FollowViewModel(username: String): ViewModel() {
    private val _following = MutableLiveData<List<UserResponse>>()
    val following: LiveData<List<UserResponse>> = _following

    private val _follower = MutableLiveData<List<UserResponse>>()
    val follower: LiveData<List<UserResponse>> = _follower

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isDataNull = MutableLiveData<Boolean>()
    val isDataNull: LiveData<Boolean> = _isLoading

    var job: Job? = null

    init {
        viewModelScope.launch {
            getFollower(username)
            getFollowing(username)
        }
    }

    private suspend fun getFollowing(username: String){
         job = CoroutineScope(Dispatchers.IO).launch {
            _isLoading.postValue(true)
             val response = ApiConfig.getApiService().getFollowing(username)
             try {
                _following.postValue(response)
                _isLoading.postValue(false)
                 if (response.isEmpty()){
                     _isDataNull.postValue(true)
                 }
             }catch (e: Exception){
                 _isLoading.postValue(false)
                 Log.e("FollowFragment", "onFailure: ${e.message.toString()}")
             }
         }
    }


    private suspend fun getFollower(username: String){
        job = CoroutineScope(Dispatchers.IO).launch {
            _isLoading.postValue(true)
            val response = ApiConfig.getApiService().getFollower(username)
            try {
                _follower.postValue(response)
                _isLoading.postValue(false)
                if (response.isEmpty()){
                    _isDataNull.postValue(true)
                }
            }catch (e: Exception){
                _isLoading.postValue(false)
                _isDataNull.postValue(true)
                Log.e("FollowFragment", "onFailure: ${e.message.toString()}")
            }
        }
    }
}