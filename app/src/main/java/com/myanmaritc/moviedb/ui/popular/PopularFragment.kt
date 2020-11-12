package com.myanmaritc.moviedb.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.myanmaritc.moviedb.R
import com.myanmaritc.moviedb.model.ResultsItem
import com.myanmaritc.moviedb.ui.adapter.MovieAdapter
import kotlinx.android.synthetic.main.fragment_popular.*

class PopularFragment : Fragment(), MovieAdapter.OnClickListener {

    private lateinit var popularViewModel: PopularViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        popularViewModel =
            ViewModelProvider(this).get(PopularViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_popular, container, false)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var popularAdapter = MovieAdapter()

        recycler_popular.apply {

            recycler_popular.layoutManager = GridLayoutManager(context, 2)
            recycler_popular.adapter = popularAdapter
        }

        popularAdapter.setOnClickListener(this)


        popularViewModel.getPopular().observe(viewLifecycleOwner, Observer { popular ->

            popularAdapter.addMovie(popular.results as List<ResultsItem>)
        })
    }

    override fun onResume() {
        super.onResume()
        popularViewModel.loadData()
    }

    override fun onClick(item: ResultsItem) {
        val directions = PopularFragmentDirections.actionNavPopularToDetailFragment(item)
        view?.findNavController()?.navigate(directions)
    }
}