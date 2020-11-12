package com.myanmaritc.moviedb.ui.upcoming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.myanmaritc.moviedb.R
import com.myanmaritc.moviedb.model.ResultsItem
import com.myanmaritc.moviedb.ui.adapter.MovieAdapter
import com.myanmaritc.moviedb.ui.nowPlaying.NowPlayingViewModel
import com.myanmaritc.moviedb.ui.popular.PopularViewModel
import kotlinx.android.synthetic.main.fragment_now_playing.*
import kotlinx.android.synthetic.main.fragment_upcoming.*
import kotlinx.android.synthetic.main.fragment_upcoming.edtSearchNowPlaying


class UpcomingFragment : Fragment(), MovieAdapter.OnClickListener {

    private val viewModel: UpcomingViewModel by viewModels()
    private val upcomingAdapter by lazy {
        MovieAdapter()
    }

//    private lateinit var upcomingViewModel: UpcomingViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        upcomingViewModel =
//            ViewModelProvider(this).get(UpcomingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_upcoming, container, false)
        // Inflate the layout for this fragment
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_upcoming.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = upcomingAdapter
        }

        upcomingAdapter.setOnClickListener(this)

        //listen text change event and search from base dataset
        edtSearchNowPlaying.addTextChangedListener { text ->
            viewModel.filterWithKeyword(keyword = text.toString())
        }

        //observing data from api
        viewModel.upComingMovieLiveData.observe(
            viewLifecycleOwner,
            Observer(::observeUpcomingMovie)
        )


//        var upcomingAdapter = MovieAdapter()
//
//        recycler_upcoming.apply {
//            recycler_upcoming.layoutManager = GridLayoutManager(context, 2)
//            recycler_upcoming.adapter = upcomingAdapter
//        }
//
//        upcomingAdapter.setOnClickListener(this)
//
//        upcomingViewModel.getUpcoming().observe(viewLifecycleOwner, Observer { upcoming ->
//            upcomingAdapter.addMovieList(upcoming.results as List<ResultsItem>)
//
//        })
    }

    private fun observeUpcomingMovie(movies: List<ResultsItem>) {
        upcomingAdapter.addMovieList(movieList = movies)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }

    override fun onClick(item: ResultsItem) {
        val directions = UpcomingFragmentDirections.actionNavUpcomingToDetailFragment(item)
        view?.findNavController()?.navigate(directions)
    }


}