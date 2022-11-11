package com.nextgen.mygithubuserapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.nextgen.mygithubuserapp.data.local.entity.UserEntity
import com.nextgen.mygithubuserapp.data.local.room.UserDao
import com.nextgen.mygithubuserapp.data.remote.response.SearchResponse
import com.nextgen.mygithubuserapp.data.remote.response.UserResponse
import com.nextgen.mygithubuserapp.data.remote.retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import kotlin.coroutines.coroutineContext

class UserRepository(
    private val apiService: ApiService,
    private val userDao: UserDao,
){
    val searchData = MutableLiveData<List<UserResponse>>()
    val loading = MutableLiveData<Boolean>()


    fun getUser(): LiveData<Result<List<UserEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUser()
            val listUser = response.map { user->
                val isFavorited = userDao.isUserFavorited(user.login)
                UserEntity(
                    user.login,  user.avatarUrl, isFavorited
                )
            }
            userDao.deleteAll()
            userDao.insertUser(listUser)
        }catch (e: Exception){
            Log.d("UserRepository", "getUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<UserEntity>>> = userDao.getUser().map { Result.Success(it) }
        emitSource(localData)
    }

    fun getDetailUser(username: String): LiveData<Result<UserEntity>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailUser(username)
            val isFavorited = userDao.isUserFavorited(response.login)
            val user = UserEntity(
                response.login, response.avatarUrl, isFavorited, response.name, response.company, response.location, response.publicRepos, response.followers, response.following
            )
            userDao.insertDetail(user)
            userDao.updateUser(user)
        }catch (e: Exception){
            Log.e("UserRepository", "getDetailUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<UserEntity>> = userDao.getDetailUser(username).map { Result.Success(it) }
        emitSource(localData)
    }


    private fun setSearchUser(username: String){
        loading.value = true
        val response = apiService.getSearchUser(username)
        response.enqueue(object: Callback<SearchResponse>{
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>,
            ) {
                if (response.isSuccessful){
                    if (response.body() != null){
                        val resp = response.body()!!.items
                        searchData.postValue(resp)
                        loading.value = false

                    }
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                loading.value = false
                Log.e("UserRepository", "onFailure: ${t.message.toString()}")
            }

        })

    }

    fun getSearch(username: String) = setSearchUser(username)

    fun getFavoritedUser(): LiveData<List<UserEntity>>{
        return userDao.getFavoriteUser()
    }

    suspend fun setFavoritedUser(user: UserEntity, userState: Boolean){
        user.isFavorite = userState
        userDao.updateUser(user)
    }

    companion object{
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userDao: UserDao
        ): UserRepository =
            instance ?: synchronized(this){
                instance ?: UserRepository(apiService, userDao)
            }.also { instance = it }
    }
}