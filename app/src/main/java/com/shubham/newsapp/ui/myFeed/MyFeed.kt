package com.shubham.newsapp.ui.myFeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.shubham.newsapp.R
import com.shubham.newsapp.data.db.entity.Article
import com.shubham.newsapp.internal.ScopedFragment
import com.shubham.newsapp.internal.verticalViewPager.ViewPagerAdapter
import kotlinx.android.synthetic.main.my_feed_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class MyFeed : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: MyFeedViewModelFactory by instance()

    private lateinit var viewModel: MyFeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.my_feed_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        val context =activity?.applicationContext
            super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(MyFeedViewModel::class.java)
        bindUI()

    }


    private fun bindUI()  = launch(Dispatchers.Main) {

        val news = viewModel.news.await()

        news.observe(this@MyFeed, Observer {
            if(it == null) return@Observer
            initViewPager(it)
        })
    }

    private fun initViewPager(it: List<Article>) {

        view_pager.adapter = ViewPagerAdapter(this@MyFeed.context,it)
        view_pager.setPageTransformer(true, ViewPageTransformer())
    }




}


class ViewPageTransformer : ViewPager.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        if(position >=0){

//            page.scaleX = 0.7f
//            page.scaleY = 0.7f - 0.05f * position

            page.translationX = -30 * position
            page.translationY = -page.height * position
        }
    }
}

