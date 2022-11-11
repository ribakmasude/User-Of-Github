package com.nextgen.mygithubuserapp.ui.detail


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.mygithubuserapp.data.UserRepository
import com.nextgen.mygithubuserapp.data.local.entity.UserEntity
import kotlinx.coroutines.*


class DetailViewModel(private val userRepository: UserRepository):ViewModel() {

    fun getDetail(username: String) = userRepository.getDetailUser(username)

    fun favUser(user: UserEntity){
        viewModelScope.launch {
            userRepository.setFavoritedUser(user, true)
        }
    }

    fun deleteUser(user: UserEntity){
        viewModelScope.launch {
            userRepository.setFavoritedUser(user, false)
        }
    }

    companion object{
        const val TAG = "DetailViewModel"
    }


}