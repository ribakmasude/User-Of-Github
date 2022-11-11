package com.nextgen.mygithubuserapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.nextgen.mygithubuserapp.databinding.ActivitySplashScreenBinding
import com.nextgen.mygithubuserapp.ui.MainActivity
import com.nextgen.mygithubuserapp.ui.setting.SettingPreferences
import com.nextgen.mygithubuserapp.ui.setting.SettingViewModel
import com.nextgen.mygithubuserapp.ui.setting.SettingViewModelFactory

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var settingViewModel: SettingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = SettingPreferences(this)
        settingViewModel = ViewModelProvider(this, SettingViewModelFactory(preferences)).get(
            SettingViewModel::class.java)

        settingViewModel.getThemeSettings().observe(this, {isDarkModeActive: Boolean ->
            setTheme(isDarkModeActive)
        })

        Handler(Looper.myLooper()!!).postDelayed({
            binding.pbSplash.visibility = View.VISIBLE
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
        binding.pbSplash.visibility = View.GONE
    }

    private fun setTheme(darkModeActive: Boolean) {
        if (darkModeActive){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            settingViewModel.saveThemeSettings(darkModeActive)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            settingViewModel.saveThemeSettings(false)
        }
    }
}