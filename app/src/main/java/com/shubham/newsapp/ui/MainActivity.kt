package com.shubham.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.shubham.newsapp.R
import com.shubham.newsapp.internal.adapters.FragmentStatePagerItemAdapter
import com.shubham.newsapp.notification.workers.ApiWorker
import com.shubham.newsapp.ui.discover.Discover
import com.shubham.newsapp.ui.myFeed.MyFeed
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var adapter: FragmentStatePagerItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setSupportActionBar(bottom_app_bar)
        val fragPagerItems = FragmentPagerItems.with(this)
            .add(R.string.discover, Discover::class.java)
            .add(R.string.myFeed, MyFeed::class.java)
            .create()

        val request = PeriodicWorkRequest.Builder(
            ApiWorker::class.java,
            Random.nextLong(15, 20),
            TimeUnit.HOURS
        )
            .setInitialDelay(15,TimeUnit.MINUTES)
            .addTag("notification")
            .build()


//        val request = OneTimeWorkRequest.Builder(
//            ApiWorker::class.java
//        )
//            .addTag("notification")
//            .build()
        val workManager = WorkManager.getInstance(applicationContext)

        workManager.cancelAllWorkByTag("notification")

        workManager.enqueue(request)
        adapter = FragmentStatePagerItemAdapter(
            supportFragmentManager,
            fragPagerItems
        )

        settings_image_view.setOnClickListener {
            Intent(this, SettingsActivity::class.java).also {
                startActivity(it)
            }
        }
        viewPager.adapter = adapter
        viewpagertab.setViewPager(viewPager)
        viewPager.currentItem = 1


        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

                if (position == 0) {

                    root_layout.animate().translationY(root_layout.top.toFloat())
                        .setInterpolator(AccelerateInterpolator()).start()


//                    bottom_app_bar.animate().translationY(-bottom_app_bar.top.toFloat())
//                        .setInterpolator(AccelerateInterpolator()).start()

                    bottom_app_bar.visibility = View.GONE

                } else {

                    root_layout.animate().translationY(-root_layout.bottom.toFloat())
                        .setInterpolator(AccelerateInterpolator()).start()

                    bottom_app_bar.visibility = View.VISIBLE

                    bottom_app_bar.animate().translationY(bottom_app_bar.bottom.toFloat())
                        .setInterpolator(AccelerateInterpolator()).start()
                }

            }

            override fun onPageSelected(position: Int) {

                when (position) {
                    0 -> showSettings()
                    1 -> showRefresh()
                }

            }

        })
    }

    override fun onResume() {
        adapter.notifyDataSetChanged()
        super.onResume()
    }


    private fun showSettings() {
        settings_image_view.setImageResource(R.drawable.ic_settings_24dp)
        refresh_image_view.setImageResource(R.drawable.ic_keyboard_arrow_right_24dp)

    }


    private fun showRefresh() {
        settings_image_view.setImageResource(R.drawable.ic_keyboard_arrow_left_24dp)
        refresh_image_view.setImageResource(R.drawable.ic_refresh_24dp)
    }

}
