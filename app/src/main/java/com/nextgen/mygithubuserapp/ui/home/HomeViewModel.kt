package com.nextgen.mygithubuserapp.ui.home

import androidx.lifecycle.*
import com.nextgen.mygithubuserapp.data.UserRepository
import com.nextgen.mygithubuserapp.data.local.entity.UserEntity
import com.nextgen.mygithubuserapp.data.remote.response.SearchResponse
import com.nextgen.mygithubuserapp.data.remote.response.UserResponse
import com.nextgen.mygithubuserapp.ui.setting.SettingPreferences
import kotlinx.coroutines.*

class HomeViewModel(private val userRepository: UserRepository, username: String) : ViewModel() {
    val listSearch: LiveData<List<UserResponse>> = userRepository.searchData
    val loading: LiveData<Boolean> = userRepository.loading

    fun getUser() = userRepository.getUser()

    init {
        viewModelScope.launch {
            getSearchUser(username)
        }
    }

    fun getSearchUser(username: String) = userRepository.getSearch(username)

    fun saveUser(user: UserEntity){
        viewModelScope.launch {
            userRepository.setFavoritedUser(user, true)
        }
    }

    fun deleteUser(user: UserEntity){
        viewModelScope.launch {
            userRepository.setFavoritedUser(user, false)
        }
    }
}