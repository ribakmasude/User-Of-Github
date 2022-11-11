package com.nextgen.mygithubuserapp.ui.setting

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map


private val Context.datastore by preferencesDataStore("settings")

class SettingPreferences(context: Context){
    private val settingDataStore = context.datastore

    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Boolean>{
        return settingDataStore.data.map { pref ->
            pref[THEME_KEY]?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean){
        settingDataStore.edit { pref->
            pref[THEME_KEY] = isDarkModeActive
        }
    }

//    companion object{
//        @Volatile
//        private var INSTANCE: SettingPreferences? = null
//        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences{
//            return INSTANCE ?: synchronized(this){
//                val instance = SettingPreferences(dataStore)
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
}