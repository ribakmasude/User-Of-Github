package com.nextgen.mygithubuserapp.ui.follow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class FollowViewModelFactory(private val username: String): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FollowViewModel::class.java)){
            return FollowViewModel(username) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class: ${modelClass.name}")
    }

}