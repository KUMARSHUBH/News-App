package com.shubham.newsapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        addPreferencesFromResource(com.shubham.newsapp.R.xml.preferences)

        val ratePreference: Preference = findPreference<Preference>("RATE") as Preference
        ratePreference.setOnPreferenceClickListener {


            (activity as SettingsActivity).smile_rating.apply {

                visibility = View.VISIBLE
                setOnRatingSelectedListener { level, reselected ->

                    Toast.makeText(this@SettingsFragment.context,"Thanks for rating!!!",Toast.LENGTH_SHORT).show()
                    GlobalScope.launch {
                        delay(4000)
                    }

                    this.visibility = View.GONE
                    return@setOnRatingSelectedListener
                }

            }

//

            return@setOnPreferenceClickListener true
        }
    }
}