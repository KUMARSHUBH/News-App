package com.shubham.newsapp.internal.adapters.verticalViewPager

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.shubham.newsapp.R
import com.shubham.newsapp.data.db.entity.Article
import com.shubham.newsapp.data.db.entity.Bookmark
import com.shubham.newsapp.internal.BlurBuilder
import com.shubham.newsapp.internal.ScopedFragment
import com.shubham.newsapp.ui.MainActivity
import com.shubham.newsapp.ui.WebViewActivity
import com.shubham.newsapp.ui.myFeed.MyFeed
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.news_item_layout.view.*


class ViewPagerAdapter(
    private val context: Context?,
    private val news: List<Article>?,
    private val bookmark: List<Bookmark>?,
    private val fragment: ScopedFragment
) : PagerAdapter() {

    private var mLayoutInflater: LayoutInflater =
        context?.applicationContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    val constraint_layout = (fragment.activity as MainActivity).root_layout
    val bottom_app_bar = (fragment.activity as MainActivity).bottom_app_bar
    val action_bar = (fragment.activity as MainActivity).supportActionBar
    private var CONSTRAINT_STATE = 0


    override fun isViewFromObject(view: View, objeect: Any): Boolean {

        return view === objeect as LinearLayout
    }

    override fun getCount(): Int {
        if(news !=null)
            return news.size

        else if(bookmark !=null)
            return bookmark.size

        else
            return 0
    }

    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    lateinit var itemView: View
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        itemView = if(preferences.getString("theme_pref","light") == "light") {

            mLayoutInflater.inflate(R.layout.news_item_layout, container, false)
        } else mLayoutInflater.inflate(R.layout.news_item_dark_layout, container, false)


        if(news!= null){

            itemView.heading_textView.text = news[position].title
            itemView.description_text_view.text = news[position].title

            if(position!=0)
                (fragment as MyFeed).link = news[position-1].title

            else (fragment as MyFeed).link = news[0].title

            fragment.bookmark.apply {

                if(position!=0){
                    title = news[position-1].title
                    url = news[position-1].url
                    author = news[position-1].author
                    content = news[position-1].content
                    description = news[position-1].title
                    publishedAt = news[position-1].publishedAt
                    source = news[position-1].source
                    urlToImage = news[position-1].urlToImage

                }

                else{

                    title = news[0].title
                    url = news[0].url
                    author = news[0].author
                    content = news[0].content
                    description = news[0].title
                    publishedAt = news[0].publishedAt
                    source = news[0].source
                    urlToImage = news[0].urlToImage

                }

            }

            constraint_layout.animate().translationY(-constraint_layout.bottom.toFloat())
                .setInterpolator(AccelerateInterpolator()).start()

            bottom_app_bar.animate().translationY(bottom_app_bar.bottom.toFloat())
                .setInterpolator(AccelerateInterpolator()).start()

            itemView.author.text = "Tap for details ... "

            Picasso.get().load(news[position].urlToImage)
                .placeholder(R.drawable.placeholder)
                .into(itemView.news_image_view, object : Callback {

                    override fun onSuccess() {
                        val bitmap = (itemView.news_image_view.drawable as BitmapDrawable).bitmap

                        var bm = BlurBuilder().blur(context!!, bitmap)

                        try {
                            bm = Bitmap.createBitmap(bm, 50, 50, 25, 25)
                        }catch (e: Exception)
                        {

                        }

                        bm = darkenBitMap(bm)
                        val drawable = BitmapDrawable(context.resources, bm)
                        itemView.author.background = drawable
                    }

                    override fun onError(e: Exception?) {

                    }

                })
        }

        else if (bookmark !=null){

            itemView.heading_textView.text = bookmark[position].title
            itemView.description_text_view.text = bookmark[position].title

            if(position!=0)
                (fragment as MyFeed).link = bookmark[position-1].title

            else (fragment as MyFeed).link = bookmark[0].title


            constraint_layout.animate().translationY(-constraint_layout.bottom.toFloat())
                .setInterpolator(AccelerateInterpolator()).start()

            bottom_app_bar.animate().translationY(bottom_app_bar.bottom.toFloat())
                .setInterpolator(AccelerateInterpolator()).start()

            itemView.author.text = "Tap for details ... "

            Picasso.get().load(bookmark[position].urlToImage)
                .placeholder(R.drawable.placeholder)
                .into(itemView.news_image_view, object : Callback {

                    override fun onSuccess() {
                        val bitmap = (itemView.news_image_view.drawable as BitmapDrawable).bitmap

                        var bm = BlurBuilder().blur(context!!, bitmap)

                        bm = Bitmap.createBitmap(bm, 50, 50, 25, 25)
                        bm = darkenBitMap(bm)
                        val drawable = BitmapDrawable(context.resources, bm)
                        itemView.author.background = drawable
                    }

                    override fun onError(e: Exception?) {

                    }

                })
        }



        itemView.description_text_view.setOnClickListener {


            if(CONSTRAINT_STATE == 1) {
                constraint_layout.animate().translationY(-constraint_layout.bottom.toFloat())
                    .setInterpolator(AccelerateInterpolator()).start()

                bottom_app_bar.animate().translationY(bottom_app_bar.bottom.toFloat())
                    .setInterpolator(AccelerateInterpolator()).start()

                CONSTRAINT_STATE = 0


            }


            else{

                constraint_layout.animate().translationY(constraint_layout.top.toFloat())
                   .setInterpolator(AccelerateInterpolator()).start()


                bottom_app_bar.animate().translationY(-bottom_app_bar.top.toFloat())
                    .setInterpolator(AccelerateInterpolator()).start()


                CONSTRAINT_STATE = 1
            }


        }



        itemView.author.setOnClickListener {

            if(news != null){


                (fragment as MyFeed).viewModel.returnFromWebView = true
                val intent = Intent(context, WebViewActivity::class.java)
                intent.putExtra("SOURCE_URL", news[position].url)
                context?.startActivity(intent)
            }
            else if(bookmark!=null){

                (fragment as MyFeed).viewModel.returnFromWebView = true
                val intent = Intent(context, WebViewActivity::class.java)
                intent.putExtra("SOURCE_URL", bookmark[position].url)
                context?.startActivity(intent)
            }


        }


        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }


    private fun darkenBitMap(bm: Bitmap): Bitmap {

        val canvas = Canvas(bm)
        val p = Paint(Color.RED)
        //ColorFilter filter = new LightingColorFilter(0xFFFFFFFF , 0x00222222); // lighten
        val filter = LightingColorFilter(-0x808081, 0x00000000)    // darken
        p.colorFilter = filter
        canvas.drawBitmap(bm, Matrix(), p)

        return bm
    }

}
