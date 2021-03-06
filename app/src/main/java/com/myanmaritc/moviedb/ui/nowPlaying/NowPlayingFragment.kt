package com.myanmaritc.moviedb.ui.nowPlaying

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.myanmaritc.moviedb.R
import com.myanmaritc.moviedb.model.ResultsItem
import com.myanmaritc.moviedb.ui.adapter.MovieAdapter
import kotlinx.android.synthetic.main.fragment_now_playing.*

class NowPlayingFragment : Fragment(), MovieAdapter.OnClickListener {

    private val viewModel: NowPlayingViewModel by viewModels()
    private val nowplayingAdapter by lazy {
        MovieAdapter()
    }

//    private lateinit var nowPlayingViewModel: NowPlayingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        nowPlayingViewModel =
//            ViewModelProvider(this).get(NowPlayingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_now_playing, container, false)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_now_playing.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = nowplayingAdapter
        }

        nowplayingAdapter.setOnClickListener(this)

        //listen text change event and search from base dataset
        edtSearchNowPlaying.addTextChangedListener { text ->
            viewModel.filterWithKeyword(keyword = text.toString())
        }

        //observing data from api
        viewModel.nowPlayingMovieLiveData.observe(
            viewLifecycleOwner,
            Observer(::observeNowPlayingMovie)
        )

//        var nowplayingAdapter = MovieAdapter()
//
//        recycler_now_playing.apply {
//            //layoutManager = LinearLayoutManager(context)
//            //adapter = nowplayingAdapter
//
//            // recycler_now_playing.layoutManager = LinearLayoutManager(context)
//
//            recycler_now_playing.layoutManager = GridLayoutManager(context, 2)
//            recycler_now_playing.adapter = nowplayingAdapter
//        }
//        nowplayingAdapter.setOnClickListener(this)
//        nowPlayingViewModel.getNowplaying().observe(viewLifecycleOwner, Observer { nowplaying ->
//            nowplayingAdapter.addMovieList(nowplaying.results as List<ResultsItem>)
//        })

    }

    private fun observeNowPlayingMovie(movies: List<ResultsItem>) {
        nowplayingAdapter.addMovieList(movieList = movies)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }

    override fun onClick(item: ResultsItem) {
        val directions = NowPlayingFragmentDirections.actionNavNowPlayingToDetailFragment(item)
        view?.findNavController()?.navigate(directions)
    }


}