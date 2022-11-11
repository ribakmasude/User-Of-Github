package com.nextgen.mygithubuserapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nextgen.mygithubuserapp.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY login ASC")
    fun getUser(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users WHERE favorited = 1")
    fun getFavoriteUser(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetail(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("DELETE FROM users WHERE favorited = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM users WHERE login = :login AND favorited = 1)")
    suspend fun isUserFavorited(login: String): Boolean

    @Query("SELECT * FROM users WHERE login = :login")
    fun getDetailUser(login: String): LiveData<UserEntity>
}