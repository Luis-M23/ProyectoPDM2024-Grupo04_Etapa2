package com.pops.z_gaming.rv_adapter.editProduct

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pops.z_gaming.databinding.RecyclerViewProductEditImagesBinding

class EditProductViewHolder(view:View) : RecyclerView.ViewHolder(view) {
    //Layout del recycler
    private val binding = RecyclerViewProductEditImagesBinding.bind(view)

    fun render(url: String) {
        Glide.with(binding.ivItemImage).load(url).into(binding.ivItemImage)
    }

}