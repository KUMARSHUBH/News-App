package com.shubham.newsapp.ui.myFeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.shubham.newsapp.R
import com.shubham.newsapp.data.db.entity.Article
import com.shubham.newsapp.ui.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
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
            initCardStackView(it.toNewsItem())
        })
    }

    private fun List<Article>.toNewsItem(): List<NewsItem>{
        return this.map {
            NewsItem(it)
        }
    }
    private fun initCardStackView(items: List<NewsItem>) {
        val groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(items)
            setOnItemClickListener { item, view ->


            }

        }

//        card_stack_view.apply {
//
//            val swipeSetting = SwipeAnimationSetting.Builder()
//                .setDirection(Direction.Bottom)
//                .setDuration(Duration.Normal.duration)
//                .setInterpolator(AccelerateInterpolator())
//                .build()
//
//
//            val rewindAnimationSetting = RewindAnimationSetting.Builder()
//                .setDirection(Direction.Top)
//                .setDuration(Duration.Normal.duration)
//                .setInterpolator(DecelerateInterpolator())
//                .build()
//
//
//
//            CardStackLayoutManager(this@MyFeed.context).also {
//
//                it.setCanScrollHorizontal(false)
//                it.setCanScrollVertical(true)
//                it.setDirections(Direction.VERTICAL)
//                it.setRewindAnimationSetting(rewindAnimationSetting)
//                it.setSwipeAnimationSetting(swipeSetting)
//
//                layoutManager = it
//            }
//
//            adapter = groupAdapter
//
//
//        }


        card_stack_view.apply {

            adapter = groupAdapter

            layoutManager = LinearLayoutManager(this@MyFeed.context,LinearLayoutManager.VERTICAL,false)

            onFlingListener = null

        }

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(card_stack_view)

    }

}

