package com.nextgen.mygithubuserapp.data.remote.retrofit

import com.nextgen.mygithubuserapp.BuildConfig
import com.nextgen.mygithubuserapp.data.remote.response.SearchResponse
import com.nextgen.mygithubuserapp.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    companion object{
        const val TOKEN_APP = BuildConfig.TOKEN_APP
    }

    @GET("users")
    @Headers("Authorization: token $TOKEN_APP", "User-Agent: request")
    suspend fun getUser(): List<UserResponse>

    @GET("users/{login}")
    @Headers("Authorization: token $TOKEN_APP", "User-Agent: request")
    suspend fun getDetailUser(
        @Path("login") login: String
    ): UserResponse

    @GET("search/users")
    @Headers("Authorization: token $TOKEN_APP", "User-Agent: request")
    fun getSearchUser(
        @Query("q") login: String
    ):Call<SearchResponse>

    @GET("users/{login}/followers")
    @Headers("Authorization: token $TOKEN_APP", "User-Agent: request")
    suspend fun getFollower(
        @Path("login") login: String
    ): List<UserResponse>

    @GET("users/{login}/following")
    @Headers("Authorization: token $TOKEN_APP", "User-Agent: request")
    suspend fun getFollowing(
        @Path("login") login: String
    ): List<UserResponse>






}