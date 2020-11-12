package com.myanmaritc.moviedb.ui.upcoming

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

class UpcomingViewModel : ViewModel() {

    val upComingMovieLiveData = MutableLiveData<List<ResultsItem>>()


    private val baseMovieList = mutableListOf<ResultsItem>()
    private val workingMovieList = mutableListOf<ResultsItem>()

    fun loadData() {
        viewModelScope.launch {
            val apiClient = MovieClient()
            val apiCall = apiClient.getUpcoming()

            apiCall.enqueue(object : Callback<Movie> {
                override fun onFailure(call: Call<Movie>, t: Throwable) {

                }

                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    val resultList = response.body()?.results as List<ResultsItem>?

                    baseMovieList.addAll(resultList!!)
                    workingMovieList.addAll(baseMovieList)
                    upComingMovieLiveData.postValue(workingMovieList)
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
            upComingMovieLiveData.postValue(workingMovieList)
        }
    }


//    private var upcomingModel: MutableLiveData<Movie> = MutableLiveData()
//
//    fun getUpcoming() = upcomingModel
//
//    fun loadData() {
//
//        var apiClient = MovieClient()
//        var apiCall = apiClient.getUpcoming()
//
//        apiCall.enqueue(object : Callback<Movie> {
//            override fun onFailure(call: Call<Movie>, t: Throwable) {
//
//            }
//
//            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
//                upcomingModel.value = response.body()
//            }
//
//        })
//    }

}