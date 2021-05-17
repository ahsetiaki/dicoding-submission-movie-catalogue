package com.setiaki.moviecatalogue.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.setiaki.moviecatalogue.ui.catalogue.CatalogueFragment

class FavoriteSectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = CatalogueFragment.newInstance(CatalogueFragment.TYPE_FAVORITE_MOVIE)
            1 -> fragment = CatalogueFragment.newInstance(CatalogueFragment.TYPE_FAVORITE_TV_SHOW)
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int = 2
}