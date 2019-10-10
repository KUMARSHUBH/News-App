package com.shubham.newsapp.internal.verticalViewPager

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.shubham.newsapp.R
import com.shubham.newsapp.data.db.entity.Article
import com.shubham.newsapp.ui.WebViewActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_item_layout.view.*

class ViewPagerAdapter(private val context: Context?, private var news: List<Article>): PagerAdapter() {

    private var mLayoutInflater: LayoutInflater = context?.applicationContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun isViewFromObject(view: View, objeect: Any): Boolean {

        return view === objeect as LinearLayout
    }

    override fun getCount(): Int {
        return news.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val itemView = mLayoutInflater.inflate(R.layout.news_item_layout,container,false)

        itemView.heading_textView.text = news[position].title
        itemView.description_text_view.text = news[position].description
        itemView.small_desc_text_view.text = news[position].author

        Picasso.get().load(news[position].urlToImage).into(itemView.news_image_view)

        itemView.setOnClickListener {
            val intent = Intent(context,WebViewActivity::class.java)
            intent.putExtra("SOURCE_URL",news[position].url)
            context?.startActivity(intent)
        }

        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}