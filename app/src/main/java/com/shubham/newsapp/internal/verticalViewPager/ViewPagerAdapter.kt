package com.shubham.newsapp.internal.verticalViewPager

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.palette.graphics.Palette
import androidx.viewpager.widget.PagerAdapter
import com.shubham.newsapp.data.db.entity.Article
import com.shubham.newsapp.internal.BlurBuilder
import com.shubham.newsapp.ui.WebViewActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_item_layout.view.*


class ViewPagerAdapter(private val context: Context?, private var news: List<Article>): PagerAdapter() {


    private lateinit var posterPalette: Palette

    private var mLayoutInflater: LayoutInflater = context?.applicationContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun isViewFromObject(view: View, objeect: Any): Boolean {

        return view === objeect as LinearLayout
    }

    override fun getCount(): Int {
        return news.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val itemView = mLayoutInflater.inflate(com.shubham.newsapp.R.layout.news_item_layout,container,false)

        itemView.heading_textView.text = news[position].title
        itemView.description_text_view.text = news[position].description

        if(news[position].author != null)
            itemView.author.text = "Source : ${news[position].author}"

        else
            itemView.author.text = "Source : Unknown"



        Picasso.get().load(news[position].urlToImage).into(itemView.news_image_view, object : Callback{

            override fun onSuccess() {
                val bitmap = (itemView.news_image_view.drawable as BitmapDrawable).bitmap

                var bm = BlurBuilder().blur(context!!,bitmap)

                bm = Bitmap.createBitmap(bm, 0, 0, 100, 50)
                val drawable = BitmapDrawable(context.resources,bm)
                itemView.author.background = drawable
            }

            override fun onError(e: Exception?) {

            }

        })

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
