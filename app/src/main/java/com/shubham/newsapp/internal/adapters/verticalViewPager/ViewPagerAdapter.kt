package com.shubham.newsapp.internal.adapters.verticalViewPager

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.shubham.newsapp.data.db.entity.Article
import com.shubham.newsapp.internal.BlurBuilder
import com.shubham.newsapp.internal.ScopedFragment
import com.shubham.newsapp.ui.MainActivity
import com.shubham.newsapp.ui.WebViewActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.news_item_layout.view.*


class ViewPagerAdapter(
    private val context: Context?,
    private var news: List<Article>,
    private val fragment: ScopedFragment
) : PagerAdapter() {

    private var mLayoutInflater: LayoutInflater =
        context?.applicationContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    val constraint_layout = (fragment.activity as MainActivity).root_layout
    val action_bar = (fragment.activity as MainActivity).supportActionBar
    private var CONSTRAINT_STATE = 0

    override fun isViewFromObject(view: View, objeect: Any): Boolean {

        return view === objeect as LinearLayout
    }

    override fun getCount(): Int {
        return news.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val itemView =
            mLayoutInflater.inflate(com.shubham.newsapp.R.layout.news_item_layout, container, false)

        itemView.heading_textView.text = news[position].title
        itemView.description_text_view.text = news[position].description

        constraint_layout.animate().translationY(-constraint_layout.bottom.toFloat())
            .setInterpolator(AccelerateInterpolator()).start()

        itemView.author.text = "Tap for details ... "

        Picasso.get().load(news[position].urlToImage)
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

        itemView.description_text_view.setOnClickListener {


            if(CONSTRAINT_STATE == 1) {
                constraint_layout.animate().translationY(-constraint_layout.bottom.toFloat())
                    .setInterpolator(AccelerateInterpolator()).start()

                CONSTRAINT_STATE = 0

//                constraint_layout.visibility = View.INVISIBLE
            }


            else{

                constraint_layout.animate().translationY(constraint_layout.top.toFloat())
                   .setInterpolator(AccelerateInterpolator()).start()

//                constraint_layout.visibility = View.VISIBLE
                CONSTRAINT_STATE = 1
            }


        }


        itemView.author.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("SOURCE_URL", news[position].url)
            context?.startActivity(intent)
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