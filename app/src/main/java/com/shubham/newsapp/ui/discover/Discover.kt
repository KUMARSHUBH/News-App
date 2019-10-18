package com.shubham.newsapp.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.shubham.newsapp.R
import com.shubham.newsapp.data.db.entity.SourceX
import com.shubham.newsapp.internal.ScopedFragment
import com.shubham.newsapp.ui.SharedViewModel
import com.shubham.newsapp.ui.SharedViewModelFactory
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.discover_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class Discover : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: SharedViewModelFactory by instance()

    private lateinit var viewModel: SharedViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.discover_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {

        val context =activity?.applicationContext
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this,viewModelFactory).get(SharedViewModel::class.java)

        viewModel = activity?.let { ViewModelProviders.of(it, viewModelFactory).get(SharedViewModel::class.java) }!!


        bindUI()

    }

    private fun bindUI() = launch(Dispatchers.Main) {

        val sources = viewModel.newsSources.await()

        sources.observe(this@Discover, Observer {entries ->
            if(entries == null) return@Observer

            initSourcesRecyclerView(entries.toNewsSourcesItem())

        })
    }

    private fun initSourcesRecyclerView(items: List<NewsSourcesItem>) {

        val groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(items)
        }
        sources_recycler_view.apply {

            layoutManager = LinearLayoutManager(this@Discover.context,LinearLayoutManager.HORIZONTAL,false)
            adapter = groupAdapter

        }

    }


    private fun List<SourceX>.toNewsSourcesItem() : List<NewsSourcesItem>{
        return this.map {
            NewsSourcesItem(this@Discover.context!!,it,this@Discover)
        }
    }

    fun shareInfo(source: String){
        viewModel.selectedDomain(source)
    }
}