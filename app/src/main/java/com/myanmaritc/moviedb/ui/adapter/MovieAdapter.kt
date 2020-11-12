package com.myanmaritc.moviedb.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myanmaritc.moviedb.R
import com.myanmaritc.moviedb.model.ResultsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var movieList: List<ResultsItem> = ArrayList()

    private val baseImg: String = "https://image.tmdb.org/t/p/w500/"

    var clickListener: OnClickListener? = null


    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        lateinit var item: ResultsItem

        fun bind(resultsItem: ResultsItem) {
            this.item = resultsItem  //get data
            Picasso.get()
                .load(baseImg + resultsItem.posterPath)
                .into(itemView.imgMovie)
            itemView.tvName.text = resultsItem.title
            itemView.tvReleaseDate.text = resultsItem.releaseDate
            itemView.tvVoteAverage.text = resultsItem.voteAverage.toString()
        }

        override fun onClick(view: View?) {
            clickListener?.onClick(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movieList.size


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    //add data
    fun addMovieList(movieList: List<ResultsItem>) {
        this.movieList = movieList
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClick(item: ResultsItem)
    }

    fun setOnClickListener(clickListener: OnClickListener) {
        this.clickListener = clickListener
    }

}