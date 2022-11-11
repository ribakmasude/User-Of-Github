package com.nextgen.mygithubuserapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class UserEntity (
    @field:ColumnInfo(name = "login")
    @field:PrimaryKey
    val login: String,

    @field:ColumnInfo(name = "avatar")
    val avatar: String? = null,

    @field:ColumnInfo(name = "favorited")
    var isFavorite: Boolean? = null,

    @field:ColumnInfo(name = "name")
    var name: String? =null,

    @field:ColumnInfo(name = "company")
    var company: String? = null,

    @field:ColumnInfo(name = "location")
    var location: String? = null,

    @field:ColumnInfo(name = "repository")
    var repository: Int? = null,

    @field:ColumnInfo(name = "follower")
    var follower: Int? = null,

    @field:ColumnInfo(name = "following")
    var following: Int? = null

)