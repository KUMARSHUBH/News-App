package com.shubham.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter
import com.shubham.newsapp.fragments.Discover
import com.shubham.newsapp.fragments.MyFeed
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val fragPagerItems = FragmentPagerItems.with(this)
            .add(R.string.discover,Discover::class.java)
            .add(R.string.myFeed,MyFeed::class.java)
            .create()
        
        val adapter = FragmentStatePagerItemAdapter(supportFragmentManager,
            fragPagerItems)
        
        viewPager.adapter = adapter
        viewpagertab.setViewPager(viewPager)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {

                when(position){
                    0 -> showSettings()
                    1 -> showRefresh()
                }

            }

        })
    }



    private fun showSettings(){
        settings_image_view.setImageResource(R.drawable.ic_settings_blue_24dp)
        refresh_image_view.setImageResource(R.drawable.ic_keyboard_arrow_right_black_24dp)

    }


    private fun showRefresh(){
        settings_image_view.setImageResource(R.drawable.ic_keyboard_arrow_left_black_24dp)
        refresh_image_view.setImageResource(R.drawable.ic_refresh_blue_24dp)
    }

}
