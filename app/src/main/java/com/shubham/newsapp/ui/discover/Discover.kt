package com.shubham.newsapp.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.shubham.newsapp.R
import com.shubham.newsapp.internal.ScopedFragment
import com.shubham.newsapp.ui.myFeed.MyFeedViewModel
import com.shubham.newsapp.ui.myFeed.MyFeedViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class Discover : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: MyFeedViewModelFactory by instance()

    private lateinit var viewModel: MyFeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.discover_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        val context =activity?.applicationContext
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(MyFeedViewModel::class.java)

    }



}
