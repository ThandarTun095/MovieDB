package com.myanmaritc.moviedb.ui.upcoming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.myanmaritc.moviedb.R
import com.myanmaritc.moviedb.model.ResultsItem
import com.myanmaritc.moviedb.ui.adapter.MovieAdapter
import com.myanmaritc.moviedb.ui.popular.PopularViewModel
import kotlinx.android.synthetic.main.fragment_upcoming.*


class UpcomingFragment : Fragment(), MovieAdapter.OnClickListener {

    private lateinit var upcomingViewModel: UpcomingViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        upcomingViewModel =
            ViewModelProvider(this).get(UpcomingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_upcoming, container, false)
        // Inflate the layout for this fragment
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var upcomingAdapter = MovieAdapter()

        recycler_upcoming.apply {
            recycler_upcoming.layoutManager = GridLayoutManager(context, 2)
            recycler_upcoming.adapter = upcomingAdapter
        }

        upcomingAdapter.setOnClickListener(this)

        upcomingViewModel.getUpcoming().observe(viewLifecycleOwner, Observer { upcoming ->
            upcomingAdapter.addMovie(upcoming.results as List<ResultsItem>)

        })
    }

    override fun onResume() {
        super.onResume()
        upcomingViewModel.loadData()
    }

    override fun onClick(item: ResultsItem) {
        val directions = UpcomingFragmentDirections.actionNavUpcomingToDetailFragment(item)
        view?.findNavController()?.navigate(directions)
    }


}