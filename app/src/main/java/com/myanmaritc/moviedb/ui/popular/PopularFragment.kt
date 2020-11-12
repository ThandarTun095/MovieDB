package com.myanmaritc.moviedb.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.myanmaritc.moviedb.R
import com.myanmaritc.moviedb.model.ResultsItem
import com.myanmaritc.moviedb.ui.adapter.MovieAdapter
import com.myanmaritc.moviedb.ui.nowPlaying.NowPlayingViewModel
import kotlinx.android.synthetic.main.fragment_now_playing.*
import kotlinx.android.synthetic.main.fragment_popular.*
import kotlinx.android.synthetic.main.fragment_popular.edtSearchNowPlaying

class PopularFragment : Fragment(), MovieAdapter.OnClickListener {

    private val viewModel: PopularViewModel by viewModels()
    private val popularAdapter by lazy {
        MovieAdapter()
    }

//    private lateinit var popularViewModel: PopularViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        popularViewModel =
//            ViewModelProvider(this).get(PopularViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_popular, container, false)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_popular.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = popularAdapter
        }

        popularAdapter.setOnClickListener(this)

        //listen text change event and search from base dataset
        edtSearchNowPlaying.addTextChangedListener { text ->
            viewModel.filterWithKeyword(keyword = text.toString())
        }

        //observing data from api
        viewModel.popularMovieLiveData.observe(
            viewLifecycleOwner,
            Observer(::observePopularMovie)
        )


//        var popularAdapter = MovieAdapter()
//
//        recycler_popular.apply {
//
//            recycler_popular.layoutManager = GridLayoutManager(context, 2)
//            recycler_popular.adapter = popularAdapter
//        }
//
//        popularAdapter.setOnClickListener(this)
//
//
//        popularViewModel.getPopular().observe(viewLifecycleOwner, Observer { popular ->
//
//            popularAdapter.addMovieList(popular.results as List<ResultsItem>)
//        })
    }

    private fun observePopularMovie(movies: List<ResultsItem>) {
        popularAdapter.addMovieList(movieList = movies)
    }
    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }

    override fun onClick(item: ResultsItem) {
        val directions = PopularFragmentDirections.actionNavPopularToDetailFragment(item)
        view?.findNavController()?.navigate(directions)
    }
}