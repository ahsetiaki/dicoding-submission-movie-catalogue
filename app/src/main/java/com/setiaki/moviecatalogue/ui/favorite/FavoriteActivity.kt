package com.setiaki.moviecatalogue.ui.favorite

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.setiaki.moviecatalogue.R
import com.setiaki.moviecatalogue.util.EspressoIdlingResource
import com.setiaki.moviecatalogue.databinding.ActivityFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteSectionPagerAdapter: FavoriteSectionPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (EspressoIdlingResource.idlingResource.isIdleNow) EspressoIdlingResource.increment()

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.favorite)

        bindTabLayout()

    }

    override fun onResume() {
        super.onResume()
        if (!EspressoIdlingResource.idlingResource.isIdleNow) EspressoIdlingResource.decrement()
    }

    private fun bindTabLayout() {
        favoriteSectionPagerAdapter = FavoriteSectionPagerAdapter(this)
        val viewPager2 = binding.vp2Catalogue
        viewPager2.adapter = favoriteSectionPagerAdapter
        val tabs = binding.tabsCatalogue
        TabLayoutMediator(tabs, viewPager2) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.movie,
            R.string.tv_show
        )
    }
}