package com.myanmaritc.moviedb.ui.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myanmaritc.moviedb.api.MovieClient
import com.myanmaritc.moviedb.model.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularViewModel : ViewModel() {

    private var popularModel: MutableLiveData<Movie> = MutableLiveData()

    fun getPopular() = popularModel

    fun loadData() {

        var apiClient = MovieClient()
        var apiCall = apiClient.getPopular()

        apiCall.enqueue(object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {

            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                popularModel.value = response.body()
            }

        })
    }

}