package com.pops.z_gaming.rv_adapter.addProduct

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pops.z_gaming.databinding.RecyclerViewAddProductImagesBinding
import com.pops.z_gaming.databinding.RecyclerViewProductEditImagesBinding

class AddProductViewHolder(view: View):  RecyclerView.ViewHolder(view){
    //Layout del recycler
    private val binding = RecyclerViewAddProductImagesBinding.bind(view)

    fun render(url: String) {
        if(url.isNotEmpty() || url != ""){
            Glide.with(binding.ivImage).load(url).into(binding.ivImage)
        }
    }
}