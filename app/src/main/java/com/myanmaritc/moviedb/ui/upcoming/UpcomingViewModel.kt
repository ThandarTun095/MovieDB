package com.myanmaritc.moviedb.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myanmaritc.moviedb.api.MovieClient
import com.myanmaritc.moviedb.model.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingViewModel : ViewModel() {

    private var upcomingModel: MutableLiveData<Movie> = MutableLiveData()

    fun getUpcoming() = upcomingModel

    fun loadData() {

        var apiClient = MovieClient()
        var apiCall = apiClient.getUpcoming()

        apiCall.enqueue(object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {

            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                upcomingModel.value = response.body()
            }

        })
    }

}