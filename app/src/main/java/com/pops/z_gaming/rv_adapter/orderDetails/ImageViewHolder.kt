package com.pops.z_gaming.rv_adapter.orderDetails

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pops.z_gaming.R
import com.pops.z_gaming.databinding.FragmentOrderDetailsBinding
import com.pops.z_gaming.databinding.RecyclerViewOrderImagesBinding

class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = RecyclerViewOrderImagesBinding.bind(view)

    /**
     * Se llamará por cada item del listado de imagenes
     */
    fun render(url: String) {
        Glide.with(binding.ivItemImage).load(url).into(binding.ivItemImage)
    }
}