package com.setiaki.moviecatalogue.ui.home

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.setiaki.moviecatalogue.ui.catalogue.CatalogueFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = CatalogueFragment.newInstance(CatalogueFragment.TYPE_MOVIE)
            1 -> fragment = CatalogueFragment.newInstance(CatalogueFragment.TYPE_TV_SHOW)
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int = 2

}