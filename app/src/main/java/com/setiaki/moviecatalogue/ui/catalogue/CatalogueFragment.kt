package com.setiaki.moviecatalogue.ui.catalogue

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.setiaki.moviecatalogue.databinding.FragmentCatalogueBinding
import com.setiaki.moviecatalogue.ui.catalogue.adapter.MovieAdapter
import com.setiaki.moviecatalogue.ui.catalogue.adapter.TvShowAdapter
import com.setiaki.moviecatalogue.ui.detail.DetailActivity
import com.setiaki.moviecatalogue.util.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogueFragment : Fragment(), CatalogueOnClickListener {
    private var _binding: FragmentCatalogueBinding? = null
    private val binding get() = _binding as FragmentCatalogueBinding

    private val catalogueViewModel: CatalogueViewModel by viewModels()

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
        setRecyclerView()
        populateRecyclerViewAdapterData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(itemId: Int) {
        val toDetailActivityIntent = Intent(activity, DetailActivity::class.java)
        toDetailActivityIntent.putExtra(DetailActivity.EXTRA_TYPE, type)
        toDetailActivityIntent.putExtra(DetailActivity.EXTRA_ITEM_ID, itemId)
        activity?.startActivity(toDetailActivityIntent)
    }

    private fun setRecyclerView() {
        val mAdapter = if (type == TYPE_MOVIE) MovieAdapter(this) else TvShowAdapter(this)
        binding.rvCatalogue.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 2)
            adapter = mAdapter
        }
    }

    private fun populateRecyclerViewAdapterData() {
        if (type == TYPE_MOVIE) {
            EspressoIdlingResource.increment()
            catalogueViewModel.getTopRatedMovies()

            catalogueViewModel.topRatedMovies.observe(viewLifecycleOwner, { movieList ->
                (binding.rvCatalogue.adapter as MovieAdapter).setMovieList(ArrayList(movieList))
                if (!EspressoIdlingResource.idlingResource.isIdleNow) EspressoIdlingResource.decrement()

            })
        } else {
            EspressoIdlingResource.increment()
            catalogueViewModel.getTopRatedTvShows()

            catalogueViewModel.topRatedTvShows.observe(viewLifecycleOwner, { tvShowList ->
                (binding.rvCatalogue.adapter as TvShowAdapter).setTVShowList(ArrayList(tvShowList))
                if (!EspressoIdlingResource.idlingResource.isIdleNow) EspressoIdlingResource.decrement()
            })
        }

    }

    companion object {
        private const val EXTRA_TYPE = "extra_type"
        const val TYPE_MOVIE = "type_movie"
        const val TYPE_TV_SHOW = "type_tv_show"

        @JvmStatic
        fun newInstance(type: String) =
            CatalogueFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_TYPE, type)
                }
            }
    }


}