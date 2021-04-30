package com.setiaki.moviecatalogue.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.setiaki.moviecatalogue.api.TMDBWebservice
import com.setiaki.moviecatalogue.databinding.ItemCatalogueBinding
import com.setiaki.moviecatalogue.response.MovieDetailResponse
import com.setiaki.moviecatalogue.ui.catalogue.CatalogueOnClickListener

class MovieAdapter internal constructor(private val listener: CatalogueOnClickListener) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var movieList = ArrayList<MovieDetailResponse>()

    fun setMovieList(movies: List<MovieDetailResponse>) {
        if (this.movieList.size > 0) this.movieList.clear()
        this.movieList.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val binding =
            ItemCatalogueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem = movieList[position]
        holder.bind(movieItem)
    }

    override fun getItemCount(): Int = movieList.size

    inner class MovieViewHolder(private val binding: ItemCatalogueBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieItem: MovieDetailResponse) {
            with(binding) {
                Glide.with(root.context)
                    .load("${TMDBWebservice.IMAGE_URL}${movieItem.posterPath}")
                    .into(imgPoster)
                tvTitle.text = movieItem.title
                root.setOnClickListener { listener.onItemClicked(movieItem.id) }
            }
        }
    }
}