package com.shubham.newsapp.ui.discover

import com.shubham.newsapp.R
import com.shubham.newsapp.data.db.entity.SourceX
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.news_source_item.*

class NewsSourcesItem(
    private val sources: SourceX
) : Item() {


    override fun bind(viewHolder: GroupieViewHolder, position: Int) {


        viewHolder.apply {

            source_name.text = sources.name

        }
    }

    override fun getLayout() = R.layout.news_source_item
}