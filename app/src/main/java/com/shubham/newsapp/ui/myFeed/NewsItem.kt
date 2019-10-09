package com.shubham.newsapp.ui.myFeed

import com.shubham.newsapp.R
import com.shubham.newsapp.data.db.entity.Article
import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.news_item_layout.*

class NewsItem(
    val article: Article
): Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.apply {

            updateImage()
            heading_textView.text = article.title
            description_text_view.text = article.description
            small_desc_text_view.text = article.author

        }
    }

    private fun GroupieViewHolder.updateImage() {

        Picasso.get().load(article.urlToImage).into(news_image_view)
    }

    override fun getLayout() = R.layout.news_item_layout

    }
