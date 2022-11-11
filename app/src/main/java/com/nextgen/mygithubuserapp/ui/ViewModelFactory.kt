package com.nextgen.mygithubuserapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nextgen.mygithubuserapp.data.UserRepository
import com.nextgen.mygithubuserapp.di.Injection
import com.nextgen.mygithubuserapp.ui.detail.DetailViewModel
import com.nextgen.mygithubuserapp.ui.favorite.FavoriteViewModel
import com.nextgen.mygithubuserapp.ui.home.HomeViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory private constructor(private val userRepository: UserRepository, private val username: String? =null): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(userRepository) as T
        }else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(userRepository) as T
        }else if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(userRepository, username!!) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class: ${modelClass.name}")
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context, username: String? = null ): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository(context), username)
            }.also { instance = it }
    }
}