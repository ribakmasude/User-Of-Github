package com.nextgen.mygithubuserapp.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.nextgen.mygithubuserapp.databinding.FragmentSettingBinding
import androidx.lifecycle.ViewModelProvider
import com.nextgen.mygithubuserapp.ui.MainActivity


class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = SettingPreferences(requireContext())
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(preferences)).get(SettingViewModel::class.java)

        settingViewModel.getThemeSettings().observe(viewLifecycleOwner, {isDarkModeActive: Boolean ->
            setTheme(isDarkModeActive)
        })
        
        _binding?.switchTheme?.setOnCheckedChangeListener { _:CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSettings(isChecked)
        }
    }



    private fun setTheme(darkModeActive: Boolean) {
        if (darkModeActive){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            _binding?.switchTheme?.isChecked = true
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            _binding?.switchTheme?.isChecked = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}