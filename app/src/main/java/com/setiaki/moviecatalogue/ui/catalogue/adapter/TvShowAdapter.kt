package com.setiaki.moviecatalogue.ui.catalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.setiaki.moviecatalogue.data.local.entity.TvShowEntity
import com.setiaki.moviecatalogue.data.remote.api.TMDBWebservice
import com.setiaki.moviecatalogue.databinding.ItemCatalogueBinding
import com.setiaki.moviecatalogue.ui.catalogue.CatalogueOnClickListener

class TvShowAdapter internal constructor(
    private val listener: CatalogueOnClickListener? = null
) : PagingDataAdapter<TvShowEntity, TvShowAdapter.TVShowViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TVShowViewHolder {
        val binding =
            ItemCatalogueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TVShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.bind(tvShow)
        }

    }


    inner class TVShowViewHolder(private val binding: ItemCatalogueBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShowEntity) {
            with(binding) {
                Glide.with(root.context)
                    .load("${TMDBWebservice.IMAGE_URL}${tvShow.posterPath}")
                    .into(imgPoster)
                tvTitle.text = tvShow.title
                root.setOnClickListener { listener?.onItemClicked(tvShow.tvShowid) }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.tvShowid == newItem.tvShowid
            }

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}