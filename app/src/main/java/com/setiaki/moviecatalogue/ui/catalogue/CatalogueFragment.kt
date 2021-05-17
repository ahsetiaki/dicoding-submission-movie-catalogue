package com.setiaki.moviecatalogue.ui.catalogue

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.setiaki.moviecatalogue.databinding.FragmentCatalogueBinding
import com.setiaki.moviecatalogue.ui.catalogue.adapter.MovieAdapter
import com.setiaki.moviecatalogue.ui.catalogue.adapter.TvShowAdapter
import com.setiaki.moviecatalogue.ui.detail.DetailActivity
import com.setiaki.moviecatalogue.util.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CatalogueFragment : Fragment(), CatalogueOnClickListener {
    private var _binding: FragmentCatalogueBinding? = null
    private val binding get() = _binding as FragmentCatalogueBinding

    private val catalogueViewModel: CatalogueViewModel by activityViewModels()

    private var type: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            type = it.getString(EXTRA_TYPE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatalogueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (EspressoIdlingResource.idlingResource.isIdleNow) EspressoIdlingResource.increment()

        setRecyclerView()
        populateRecyclerViewAdapterData()

    }

    override fun onResume() {
        super.onResume()
        if (!EspressoIdlingResource.idlingResource.isIdleNow) EspressoIdlingResource.decrement()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(itemId: Int) {
        val detailActivityType = when (type) {
            TYPE_HOME_MOVIE -> DetailActivity.TYPE_MOVIE
            TYPE_FAVORITE_MOVIE -> DetailActivity.TYPE_MOVIE
            TYPE_HOME_TV_SHOW -> DetailActivity.TYPE_TV_SHOW
            TYPE_FAVORITE_TV_SHOW -> DetailActivity.TYPE_TV_SHOW
            else -> null
        }
        val toDetailActivityIntent = Intent(activity, DetailActivity::class.java)
        toDetailActivityIntent.putExtra(DetailActivity.EXTRA_TYPE, detailActivityType)
        toDetailActivityIntent.putExtra(DetailActivity.EXTRA_ITEM_ID, itemId)
        activity?.startActivity(toDetailActivityIntent)
    }

    private fun setRecyclerView() {
        val mAdapter = when (type) {
            TYPE_HOME_MOVIE -> MovieAdapter(this)
            TYPE_FAVORITE_MOVIE -> MovieAdapter(this)
            TYPE_HOME_TV_SHOW -> TvShowAdapter(this)
            TYPE_FAVORITE_TV_SHOW -> TvShowAdapter(this)
            else -> null
        }
        binding.rvCatalogue.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = mAdapter
        }
    }

    private fun populateRecyclerViewAdapterData() {
        when (type) {
            TYPE_HOME_MOVIE -> {
                catalogueViewModel.getTopRatedMovies().observe(viewLifecycleOwner, { pagingMovies ->
                    lifecycleScope.launch {
                        (binding.rvCatalogue.adapter as MovieAdapter).submitData(pagingMovies)
                    }
                })
            }
            TYPE_FAVORITE_MOVIE -> {
                catalogueViewModel.getFavoritedMovies()
                    .observe(viewLifecycleOwner, { pagingMovies ->
                        lifecycleScope.launch {
                            (binding.rvCatalogue.adapter as MovieAdapter).submitData(pagingMovies)
                        }
                    })
            }
            TYPE_HOME_TV_SHOW -> {
                catalogueViewModel.getTopRatedTvShows()
                    .observe(viewLifecycleOwner, { pagingTvShows ->
                        lifecycleScope.launch {
                            (binding.rvCatalogue.adapter as TvShowAdapter).submitData(pagingTvShows)
                        }
                    })
            }
            TYPE_FAVORITE_TV_SHOW -> {
                catalogueViewModel.getFavoritedTvShows()
                    .observe(viewLifecycleOwner, { pagingTvShows ->
                        lifecycleScope.launch {
                            (binding.rvCatalogue.adapter as TvShowAdapter).submitData(pagingTvShows)
                        }
                    })
            }
        }
    }

    companion object {
        private const val EXTRA_TYPE = "extra_type"
        const val TYPE_HOME_MOVIE = "type_home_movie"
        const val TYPE_HOME_TV_SHOW = "type_home_tv_show"
        const val TYPE_FAVORITE_MOVIE = "type_favorite_movie"
        const val TYPE_FAVORITE_TV_SHOW = "type_favorite_tv_show"

        @JvmStatic
        fun newInstance(type: String) =
            CatalogueFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_TYPE, type)
                }
            }
    }


}