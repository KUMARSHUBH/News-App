package com.shubham.newsapp.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.shubham.newsapp.R

class Discover : Fragment() {

    companion object {
        fun newInstance() = Discover()
    }

    private lateinit var viewModel: DiscoverViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.discover_fragment, container, false)


        view.setOnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_UP)
                Toast.makeText(this.context,"Swiped Down", Toast.LENGTH_SHORT).show()
            return@setOnTouchListener true
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DiscoverViewModel::class.java)
        // TODO: Use the ViewModel

    }

}
