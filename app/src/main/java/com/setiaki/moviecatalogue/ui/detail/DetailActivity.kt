package com.setiaki.moviecatalogue.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.setiaki.moviecatalogue.R
import com.setiaki.moviecatalogue.data.local.entity.GenreEntity
import com.setiaki.moviecatalogue.data.remote.api.TMDBWebservice
import com.setiaki.moviecatalogue.util.EspressoIdlingResource
import com.setiaki.moviecatalogue.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel: DetailViewModel by viewModels()

    private var type: String? = ""
    private var itemId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (EspressoIdlingResource.idlingResource.isIdleNow) EspressoIdlingResource.increment()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        type = intent.getStringExtra(EXTRA_TYPE)
        itemId = intent.getIntExtra(EXTRA_ITEM_ID, 0)

        bindDetail()

    }

    override fun onResume() {
        super.onResume()
        if (!EspressoIdlingResource.idlingResource.isIdleNow) EspressoIdlingResource.decrement()
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.fab_favorite) {
            toggleFavoriteButton()
        }
    }

    private fun bindDetail() {
        if (type == TYPE_MOVIE) {
            detailViewModel.getMovieDetail(itemId).observe(this, { resource ->
                val movie = resource.data?.movie
                val genres = resource.data?.genres
                supportActionBar?.title = movie?.title
                with(binding) {
                    Glide.with(root.context)
                        .load("${TMDBWebservice.IMAGE_URL}${movie?.posterPath}")
                        .into(imgPoster)
                    tvTitle.text = movie?.title
                    tvReleaseDate.text = movie?.releaseDate
                    tvGenres.text = convertGenres(genres)
                    tvVoteAverage.text = movie?.voteAverage.toString()
                    tvOverview.text = movie?.overview
                    fabFavorite.setImageDrawable(getFavoriteButtonDrawable(movie?.isFavorited!!))
                    binding.fabFavorite.setOnClickListener(this@DetailActivity)
                }
            })
        } else {
            detailViewModel.getTvShowDetail(itemId).observe(this, { resource ->
                val tvShow = resource.data?.tvShow
                val genres = resource.data?.genres
                supportActionBar?.title = tvShow?.title
                with(binding) {
                    Glide.with(root.context)
                        .load("${TMDBWebservice.IMAGE_URL}${tvShow?.posterPath}")
                        .into(imgPoster)
                    tvTitle.text = tvShow?.title
                    tvReleaseDate.text = tvShow?.releaseDate
                    tvGenres.text = convertGenres(genres)
                    tvVoteAverage.text = tvShow?.voteAverage.toString()
                    tvOverview.text = tvShow?.overview
                    fabFavorite.setImageDrawable(getFavoriteButtonDrawable(tvShow?.isFavorited!!))
                    binding.fabFavorite.setOnClickListener(this@DetailActivity)
                }
            })
        }
    }

    private fun convertGenres(genreResponses: List<GenreEntity>?): String {
        val listGenreName = arrayListOf<String>()
        genreResponses?.forEach { listGenreName.add(it.name) }
        return listGenreName.joinToString()
    }

    private fun toggleFavoriteButton() {
        if (type == TYPE_MOVIE) {
            detailViewModel.toggleMovieFavoriteStatus(itemId)
        } else {
            detailViewModel.toggleTvShowFavoriteStatus(itemId)
        }
    }

    private fun getFavoriteButtonDrawable(isFavorited: Boolean): Drawable? {
        return if (isFavorited) {
            ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite_filled, null)
        } else {
            ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite_border, null)
        }
    }

    companion object {
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_ITEM_ID = "extra_item_id"
        const val TYPE_MOVIE = "type_movie"
        const val TYPE_TV_SHOW = "type_tv_show"
    }


}