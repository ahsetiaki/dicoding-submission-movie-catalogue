package com.setiaki.moviecatalogue.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.setiaki.moviecatalogue.api.TMDBWebservice
import com.setiaki.moviecatalogue.databinding.ActivityDetailBinding
import com.setiaki.moviecatalogue.response.Genre
import com.setiaki.moviecatalogue.ui.catalogue.CatalogueFragment
import com.setiaki.moviecatalogue.util.EspressoIdlingResource

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel: DetailViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.getStringExtra(EXTRA_TYPE)
        val itemId = intent.getIntExtra(EXTRA_ITEM_ID, 0)

        if (type == CatalogueFragment.TYPE_MOVIE) {
            bindMovieDetail(itemId)
        } else {
            bindTvShowDetail(itemId)
        }

    }

    private fun bindMovieDetail(itemId: Int) {
        EspressoIdlingResource.increment()
        detailViewModel.getMovieDetail(itemId)

        detailViewModel.movieDetail.observe(this, { movieItem ->
            supportActionBar?.title = movieItem.title
            with(binding) {
                Glide.with(root.context)
                    .load("${TMDBWebservice.IMAGE_URL}${movieItem?.posterPath}")
                    .into(imgPoster)
                tvTitle.text = movieItem?.title
                tvReleaseDate.text = movieItem?.releaseDate
                tvGenres.text = convertGenres(movieItem?.genres)
                tvVoteAverage.text = movieItem?.voteAverage.toString()
                tvOverview.text = movieItem?.overview
            }
            if(!EspressoIdlingResource.idlingResource.isIdleNow) EspressoIdlingResource.decrement()
        })
    }

    private fun bindTvShowDetail(itemId: Int) {
        EspressoIdlingResource.increment()
        detailViewModel.getTvShowDetail(itemId)

        detailViewModel.tvShowDetail.observe(this, { tvShowItem ->
            supportActionBar?.title = tvShowItem.title
            with(binding) {
                Glide.with(root.context)
                    .load("${TMDBWebservice.IMAGE_URL}${tvShowItem?.posterPath}")
                    .into(imgPoster)
                tvTitle.text = tvShowItem?.title
                tvReleaseDate.text = tvShowItem?.releaseDate
                tvGenres.text = convertGenres(tvShowItem?.genres)
                tvVoteAverage.text = tvShowItem?.voteAverage.toString()
                tvOverview.text = tvShowItem?.overview
            }
            if(!EspressoIdlingResource.idlingResource.isIdleNow) EspressoIdlingResource.decrement()
        })
    }

    private fun convertGenres(genres: List<Genre>?): String {
        val listGenreName = arrayListOf<String>()
        genres?.forEach { listGenreName.add(it.name) }
        return listGenreName.joinToString()
    }


    companion object {
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_ITEM_ID = "extra_item_id"
    }
}