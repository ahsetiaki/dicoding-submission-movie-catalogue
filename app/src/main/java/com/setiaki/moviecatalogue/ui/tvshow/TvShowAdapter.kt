package com.setiaki.moviecatalogue.ui.tvshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.setiaki.moviecatalogue.api.TMDBWebservice
import com.setiaki.moviecatalogue.databinding.ItemCatalogueBinding
import com.setiaki.moviecatalogue.response.TvShowDetailResponse
import com.setiaki.moviecatalogue.ui.catalogue.CatalogueOnClickListener

class TvShowAdapter internal constructor(private val listener: CatalogueOnClickListener) :
    RecyclerView.Adapter<TvShowAdapter.TVShowViewHolder>() {
    private var tvShowList = ArrayList<TvShowDetailResponse>()

    fun setTVShowList(tvShows: List<TvShowDetailResponse>) {
        if (tvShowList.size > 0) tvShowList.clear()
        tvShowList.addAll(tvShows)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvShowAdapter.TVShowViewHolder {
        val binding =
            ItemCatalogueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TVShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvShowAdapter.TVShowViewHolder, position: Int) {
        val tvShowItem = tvShowList[position]
        holder.bind(tvShowItem)
    }

    override fun getItemCount(): Int = tvShowList.size

    inner class TVShowViewHolder(private val binding: ItemCatalogueBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShowItem: TvShowDetailResponse) {
            with(binding) {
                Glide.with(root.context)
                    .load("${TMDBWebservice.IMAGE_URL}${tvShowItem.posterPath}")
                    .into(imgPoster)
                tvTitle.text = tvShowItem.title
                root.setOnClickListener { listener.onItemClicked(tvShowItem.id) }
            }
        }
    }
}