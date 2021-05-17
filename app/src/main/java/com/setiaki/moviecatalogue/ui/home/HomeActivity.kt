package com.setiaki.moviecatalogue.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.setiaki.moviecatalogue.R
import com.setiaki.moviecatalogue.util.EspressoIdlingResource
import com.setiaki.moviecatalogue.databinding.ActivityHomeBinding
import com.setiaki.moviecatalogue.ui.favorite.FavoriteActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeSectionPagerAdapter: HomeSectionPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (EspressoIdlingResource.idlingResource.isIdleNow) EspressoIdlingResource.increment()

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.top_rated)

        bindTabLayout()

    }

    override fun onResume() {
        super.onResume()
        if (!EspressoIdlingResource.idlingResource.isIdleNow) EspressoIdlingResource.decrement()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menus, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                val favoriteActivityIntent = Intent(this@HomeActivity, FavoriteActivity::class.java)
                startActivity(favoriteActivityIntent)
                true
            }
            else -> true
        }
    }

    private fun bindTabLayout() {
        homeSectionPagerAdapter = HomeSectionPagerAdapter(this)
        val viewPager2 = binding.vp2Catalogue
        viewPager2.adapter = homeSectionPagerAdapter
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