package com.shubham.newsapp.ui.discover

import android.content.Context
import android.os.Bundle
import com.shubham.newsapp.R
import com.shubham.newsapp.data.db.entity.SourceX
import com.shubham.newsapp.internal.ScopedFragment
import com.shubham.newsapp.internal.getHostName
import com.shubham.newsapp.ui.MainActivity
import com.shubham.newsapp.ui.myFeed.MyFeed
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.news_source_item.*

class NewsSourcesItem(
    private val context: Context,
    private val sources: SourceX,
    private val fragment: ScopedFragment
) : Item() {

    val args: Bundle = Bundle()

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {


        viewHolder.apply {

            source_name.text = sources.name

            source_image.setOnClickListener {

                val frag = MyFeed()

                args.putString("SOURCE_URL", getHostName(sources.url))
                frag.arguments = args

                val vp = (fragment.activity as MainActivity).viewPager

                vp.viewPager.currentItem = vp.currentItem + 1

            }
        }
    }

    override fun getLayout() = R.layout.news_source_item
}