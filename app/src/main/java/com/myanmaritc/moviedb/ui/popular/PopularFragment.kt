package com.myanmaritc.moviedb.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.myanmaritc.moviedb.R

class PopularFragment : Fragment() {

    private lateinit var popularViewModel: PopularViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        popularViewModel =
                ViewModelProviders.of(this).get(PopularViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_popular, container, false)

        popularViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }
}