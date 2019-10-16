package com.shubham.newsapp.ui.discover

import android.content.Context
import com.shubham.newsapp.R
import com.shubham.newsapp.data.db.entity.SourceX
import com.shubham.newsapp.internal.ScopedFragment
import com.shubham.newsapp.ui.MainActivity
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

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {


        viewHolder.apply {

            source_name.text = sources.name

            source_image.setOnClickListener {


//                args.putString("SOURCE_URL", getHostName(sources.url))
//                frag.arguments = args

                (fragment as Discover).shareInfo(sources.url)

//                Toast.makeText(context, sources.url,Toast.LENGTH_SHORT).show()

                val vp = (fragment.activity as MainActivity).viewPager

                vp.viewPager.currentItem = vp.currentItem + 1

            }
        }
    }

    override fun getLayout() = R.layout.news_source_item
}