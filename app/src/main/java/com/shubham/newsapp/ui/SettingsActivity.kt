package com.shubham.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shubham.newsapp.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setTheme(R.style.AppTheme_Settings)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Settings"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowCustomEnabled(true)

        if(fragment_container != null){

            if(savedInstanceState != null)
                return

            supportFragmentManager.beginTransaction().add(R.id.fragment_container, SettingsFragment()).commit()
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
