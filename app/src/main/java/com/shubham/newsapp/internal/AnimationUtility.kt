package com.shubham.newsapp.internal

import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.view.animation.*
import android.widget.LinearLayout
import android.widget.TextView


object AnimationUtility {

    // region Constants
    val DURATION = 100L
    val DURATION_2 = 400L
    // endregion

    // region Static Variables
    private var fastOutSlowIn: Interpolator? = null
    private var fastOutLinearIn: Interpolator? = null
    private var linearOutSlowIn: Interpolator? = null
    private var linear: Interpolator? = null

    val linearInterpolator: Interpolator
        get() {
            if (linear == null) {
                linear = LinearInterpolator()
            }
            return linear as Interpolator
        }
    // endregion

    fun animateBackgroundColorChange(view: View, startColor: Int, endColor: Int) {
        val infoBackgroundColorAnim = ValueAnimator.ofArgb(startColor, endColor)
        infoBackgroundColorAnim.addUpdateListener { animation -> view.setBackgroundColor(animation.animatedValue as Int) }
        infoBackgroundColorAnim.duration = DURATION
        infoBackgroundColorAnim.interpolator = getFastOutSlowInInterpolator(view.context)
        infoBackgroundColorAnim.start()
    }

    fun animateTextColorChange(tv: TextView, startColor: Int, endColor: Int) {
        val titleTextColorAnim = ValueAnimator.ofArgb(startColor, endColor)
        titleTextColorAnim.addUpdateListener { animation -> tv.setTextColor(animation.animatedValue as Int) }
        titleTextColorAnim.duration = DURATION
        titleTextColorAnim.interpolator = getFastOutSlowInInterpolator(tv.context)
        titleTextColorAnim.start()
    }

    fun getExpandHeightAnimation(v: View, targetHeight: Int): Animation {
        return object : Animation() {
             override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                v.layoutParams.height = if (interpolatedTime == 1f)
                    LinearLayout.LayoutParams.WRAP_CONTENT
                else
                    (targetHeight * interpolatedTime).toInt()
                v.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
    }

    fun getTargetHeight(v: View): Int {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        v.measure(widthSpec, heightSpec)

        return v.measuredHeight
    }


    private fun getInitialHeight(v: View): Int {
        return v.measuredHeight
    }

    fun expand(v: View) {
        val targetHeight = getTargetHeight(v)

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.layoutParams.height = 1
        v.visibility = View.VISIBLE
        //        Animation a = new Animation(){
        //            @Override
        //            protected void applyTransformation(float interpolatedTime, Transformation t) {
        //                v.getLayoutParams().height = interpolatedTime == 1
        //                        ? LinearLayout.LayoutParams.WRAP_CONTENT
        //                        : (int)(targetHeight * interpolatedTime);
        //                v.requestLayout();
        //            }
        //
        //            @Override
        //            public boolean willChangeBounds() {
        //                return true;
        //            }
        //        };

        val animation = AnimationUtility.getExpandHeightAnimation(v, targetHeight)


        // 6dp/ms
        //        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density) * 6);
        animation.duration = DURATION_2
        //        a.setInterpolator(new DecelerateInterpolator());
        v.startAnimation(animation)
    }

    fun collapse(v: View) {
        val initialHeight = getInitialHeight(v)

        val a = object : Animation() {
             override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (interpolatedTime == 1f) {
                    v.visibility = View.GONE
                } else {
                    v.layoutParams.height =
                        initialHeight - (initialHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        // 6dp/ms
        //        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density) * 6);
        a.duration = DURATION_2
        //        a.setInterpolator(new DecelerateInterpolator());

        v.startAnimation(a)
    }

    fun getFastOutSlowInInterpolator(context: Context): Interpolator? {
        if (fastOutSlowIn == null) {
            fastOutSlowIn = AnimationUtils.loadInterpolator(
                context,
                android.R.interpolator.fast_out_slow_in
            )
        }
        return fastOutSlowIn
    }

    fun getFastOutLinearInInterpolator(context: Context): Interpolator? {
        if (fastOutLinearIn == null) {
            fastOutLinearIn = AnimationUtils.loadInterpolator(
                context,
                android.R.interpolator.fast_out_linear_in
            )
        }
        return fastOutLinearIn
    }

    fun getLinearOutSlowInInterpolator(context: Context): Interpolator? {
        if (linearOutSlowIn == null) {
            linearOutSlowIn = AnimationUtils.loadInterpolator(
                context,
                android.R.interpolator.linear_out_slow_in
            )
        }
        return linearOutSlowIn
    }
}// endregion
// region Constructors
