package com.shubham.newsapp.ui.myFeed

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.shubham.newsapp.NewsApplication
import com.shubham.newsapp.R
import com.shubham.newsapp.data.db.entity.Article
import com.shubham.newsapp.internal.ScopedFragment
import com.shubham.newsapp.internal.adapters.verticalViewPager.ViewPagerAdapter
import com.shubham.newsapp.internal.getHostName
import com.shubham.newsapp.ui.MainActivity
import com.shubham.newsapp.ui.SharedViewModel
import com.shubham.newsapp.ui.SharedViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.my_feed_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class MyFeed : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: SharedViewModelFactory by instance()

    lateinit var viewModel: SharedViewModel

    var link: String? = null
    var source: String? = null
    var selectedItem: String? = null
    var category: String? = null
    var searchView: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.my_feed_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {

        val context = activity?.applicationContext
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this,viewModelFactory).get(SharedViewModel::class.java)

        viewModel = activity?.let {
            ViewModelProviders.of(it, viewModelFactory).get(SharedViewModel::class.java)
        }!!
        shimmer_layout.apply {
            startShimmerAnimation()
        }

        val img = (activity as? MainActivity)?.refresh_image_view
        img?.setOnClickListener {

            val anim = AnimationUtils.loadAnimation(this.context, R.anim.rotate)
            it.startAnimation(anim)
            shimmer_layout.visibility = View.VISIBLE
            shimmer_layout.startShimmerAnimation()

//            bindUI()
        }

        val share = (activity as MainActivity).share.setOnClickListener {

            shareNews()
        }
    }

    private fun shareNews() {

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Check out this news article\n\n$link"
        )
        shareIntent.type = "text/plain"
        startActivity(Intent.createChooser(shareIntent, "Share this article with..."))
    }

    private fun bindUI() = launch(Dispatchers.Main) {

        shimmer_layout.apply {
            visibility = View.VISIBLE
            startShimmerAnimation()
        }

        val news: LiveData<List<Article>>


        if(activity?.intent?.getBooleanExtra("from_Notification",false) == true){

            viewModel.returnFromWebView = false
            viewModel.selectedDomain(null)
            viewModel.selectedItem(null)
            viewModel.selectedCategory(null)
            viewModel.selectedKeyword(null)

            viewModel.fetchNewsFromNotification(activity?.intent?.getStringExtra("title")!!)
            news = viewModel.newsNotification.await()

            news.observe(this@MyFeed, Observer {
                if (it == null) return@Observer

                initViewPager(it)

            })
        }
        else{

            if (source != null) {

                viewModel.fetchNewsFromSources()
                news = viewModel.newsFromSources.await()
                news.observe(this@MyFeed, Observer {
                    if (it == null) {
                        return@Observer
                    }

                    if (it.isEmpty())
                        Log.e("ERROR", "THIs IS ERROR")
                    //Toast.makeText(this@MyFeed.context, "Error", Toast.LENGTH_SHORT).show()

                    else
                        initViewPager(it)
                })

            }

            else {

                if (selectedItem != null) {

                    if (selectedItem == "all_news") {

                        viewModel.fetchAllNews()
                        news = viewModel.allNews.await()

                        news.observe(this@MyFeed, Observer {
                            if (it == null) return@Observer

                            initViewPager(it)
                        })
                    } else if (selectedItem == "top_stories") {
                        viewModel.fetchTopNews()
                        news = viewModel.topNews.await()

                        news.observe(this@MyFeed, Observer {
                            if (it == null) return@Observer

                            initViewPager(it)

                        })

                    }
                }

                else {

                    if (category != null) {

                        viewModel.fetchTopHeadlinesCategory()

                        news = viewModel.topNewsCategory.await()

                        news.observe(this@MyFeed, Observer {
                            if (it == null) return@Observer

                            initViewPager(it)
                        })
                    }


                    else {

                        if (searchView != null){

                            viewModel.fetchNews()

                            news = viewModel.newsSearch.await()

                            news.observe(this@MyFeed, Observer {
                                if (it == null) return@Observer

                                initViewPager(it)
                            })
                        }

                        else{

                            news = viewModel.news.await()

                            news.observe(this@MyFeed, Observer {
                                if (it == null) return@Observer

                                initViewPager(it)
                            })
                        }
                    }
                }

            }
        }


    }

    private fun initViewPager(it: List<Article>) {

        shimmer_layout.stopShimmerAnimation()
        shimmer_layout.visibility = View.INVISIBLE
        view_pager.adapter = ViewPagerAdapter(this@MyFeed.context, it, this)

        view_pager.setPageTransformer(true, ViewPageTransformer())

//        Toast.makeText(this.context, "Feed updated", Toast.LENGTH_SHORT).show()

    }

    override fun onPause() {
        viewModel.returnFromWebView = true
        (this@MyFeed.activity?.application as NewsApplication).preferencesChanged = false
        activity?.intent?.removeExtra("from_Notification")
        super.onPause()
    }

    override fun onResume() {

        super.onResume()


        val domain = viewModel.returnDomain()
        selectedItem = viewModel.returnSelected()
        category = viewModel.returnCategory()
        searchView = viewModel.returnKeyword()


        source = if (domain != null)
            getHostName(domain)
        else
            null


//        bindUI()
        if(!viewModel.returnFromWebView || (this@MyFeed.activity?.application as NewsApplication).preferencesChanged)
            bindUI()

        if (source != null)
            Toast.makeText(this@MyFeed.context, "$source", Toast.LENGTH_SHORT).show()

    }

}


class ViewPageTransformer : ViewPager.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        if (position <= 0) {

            page.translationX = page.width * -position
            page.translationY = page.height * position

        } else {

            page.translationX = page.width * -position
            page.translationY = page.height * position * 0.5f
            page.setBackgroundColor(Color.BLACK)

        }

    }

}