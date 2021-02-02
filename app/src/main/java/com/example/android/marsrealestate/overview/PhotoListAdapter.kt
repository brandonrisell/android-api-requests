package com.example.android.marsrealestate.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.android.marsrealestate.R
import com.example.android.marsrealestate.databinding.GridViewItemBinding
import com.example.android.marsrealestate.network.MarsPropertyListing

class PhotoListAdapter(private val onClick: (property: MarsPropertyListing) -> Unit) : ListAdapter<MarsPropertyListing, MarsPropertyViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPropertyViewHolder {
        return MarsPropertyViewHolder.from(parent, onClick)
    }

    override fun onBindViewHolder(holder: MarsPropertyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class MarsPropertyViewHolder(private val view: View, private val onClick: (property: MarsPropertyListing) -> Unit) :
    RecyclerView.ViewHolder(view) {

    fun bind(marsPropertyListing: MarsPropertyListing) {
        val viewBinding = GridViewItemBinding.bind(view)

        viewBinding.marsPropertyType.visibility =
            if (marsPropertyListing.isRental) View.GONE else View.VISIBLE

        viewBinding.marsImage.load(marsPropertyListing.imgSrcUrl.replace("http", "https")) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
        view.setOnClickListener { onClick(marsPropertyListing) }
    }

    companion object {
        fun from(parent: ViewGroup, onClick: (property: MarsPropertyListing) -> Unit): MarsPropertyViewHolder {
            val layoutInflater =
                LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(
                    R.layout.grid_view_item,
                    parent, false
                )
            return MarsPropertyViewHolder(view, onClick)
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<MarsPropertyListing>() {
    override fun areItemsTheSame(
        oldItem: MarsPropertyListing,
        newItem: MarsPropertyListing
    ): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(
        oldItem: MarsPropertyListing,
        newItem: MarsPropertyListing
    ): Boolean {
        return oldItem.id == newItem.id
    }
}
