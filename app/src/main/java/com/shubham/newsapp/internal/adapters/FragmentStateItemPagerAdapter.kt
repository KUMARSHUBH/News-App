package com.shubham.newsapp.internal.adapters

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import java.lang.ref.WeakReference


class FragmentStatePagerItemAdapter(fm: FragmentManager, private val pages: FragmentPagerItems) :

    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val holder: SparseArrayCompat<WeakReference<Fragment>> = SparseArrayCompat(pages.size)

    override fun getCount(): Int {
        return pages.size
    }

    override fun getItem(position: Int): Fragment {
        return getPagerItem(position).instantiate(pages.context, position)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item = super.instantiateItem(container, position)
        if (item is Fragment) {
            holder.put(position, WeakReference(item))
        }
        return item
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        holder.remove(position)
        super.destroyItem(container, position, `object`)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return getPagerItem(position).title
    }

    fun changePageTitle(title: CharSequence){
        getPagerItem(1).title
    }

    override fun getPageWidth(position: Int): Float {
        return getPagerItem(position).width
    }

    fun getPage(position: Int): Fragment? {
        val weakRefItem = holder.get(position)
        return weakRefItem?.get()
    }

    protected fun getPagerItem(position: Int): FragmentPagerItem {
        return pages[position]
    }

}
