package com.nextgen.mygithubuserapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.mygithubuserapp.data.UserRepository
import com.nextgen.mygithubuserapp.data.local.entity.UserEntity
import com.nextgen.mygithubuserapp.ui.setting.SettingPreferences
import kotlinx.coroutines.launch

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getFavoriteUser() = userRepository.getFavoritedUser()

    fun deleteUser(user: UserEntity){
        viewModelScope.launch {
            userRepository.setFavoritedUser(user, false)
        }
    }

}