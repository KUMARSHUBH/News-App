package com.shubham.newsapp.ui

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.shubham.newsapp.R

class SettingsFragment : PreferenceFragmentCompat(){

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        addPreferencesFromResource(R.xml.preferences)
    }
}