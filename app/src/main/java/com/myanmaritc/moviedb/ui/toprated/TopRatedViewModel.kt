package com.myanmaritc.moviedb.ui.toprated

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myanmaritc.moviedb.api.MovieClient
import com.myanmaritc.moviedb.model.Movie
import com.myanmaritc.moviedb.model.ResultsItem
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopRatedViewModel : ViewModel() {

    val topRatedMovieLiveData = MutableLiveData<List<ResultsItem>>()


    private val baseMovieList = mutableListOf<ResultsItem>()
    private val workingMovieList = mutableListOf<ResultsItem>()

    fun loadData() {
        viewModelScope.launch {
            val apiClient = MovieClient()
            val apiCall = apiClient.getToprated()

            apiCall.enqueue(object : Callback<Movie> {
                override fun onFailure(call: Call<Movie>, t: Throwable) {

                }

                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    val resultList = response.body()?.results as List<ResultsItem>?

                    baseMovieList.addAll(resultList!!)
                    workingMovieList.addAll(baseMovieList)
                    topRatedMovieLiveData.postValue(workingMovieList)
                }

            })

        }

    }

    fun filterWithKeyword(keyword: String) {
        viewModelScope.launch {

            if (keyword.isNotEmpty()) {
                val searchResult = baseMovieList.filter { resultsItem ->
                    resultsItem.title!!.contains(
                        keyword,
                        ignoreCase = true
                    )
                }

                workingMovieList.clear()
                workingMovieList.addAll(searchResult)
            } else {
                workingMovieList.clear()
                workingMovieList.addAll(baseMovieList)
            }
            topRatedMovieLiveData.postValue(workingMovieList)
        }
    }


//    private var topRatedViewModel: MutableLiveData<Movie> = MutableLiveData()
//
//    fun gettopRated() = topRatedViewModel
//
//    fun loadData() {
//
//        var apiClient = MovieClient()
//        var apiCall = apiClient.getToprated()
//
//        apiCall.enqueue(object : Callback<Movie> {
//            override fun onFailure(call: Call<Movie>, t: Throwable) {
//
//            }
//
//            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
//                topRatedViewModel.value = response.body()
//            }
//
//        })
//    }
}