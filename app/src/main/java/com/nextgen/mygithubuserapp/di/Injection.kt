package com.nextgen.mygithubuserapp.di

import android.content.Context
import com.nextgen.mygithubuserapp.data.UserRepository
import com.nextgen.mygithubuserapp.data.local.room.UserDatabase
import com.nextgen.mygithubuserapp.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        return UserRepository.getInstance(apiService, dao)
    }
}