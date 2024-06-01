package com.pops.z_gaming.rv_adapter.favorites

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pops.z_gaming.Model.Products
import com.pops.z_gaming.databinding.FavoriteItemsBinding
class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view)  {

    val binding = FavoriteItemsBinding.bind(view)


    fun render(productModel: Products, onClickListener:(Products)->Unit) {
        binding.tvModel.text = productModel.model
        binding.tvName.text = productModel.name
        binding.tvPrice.text = productModel.price
        Glide.with(binding.ivProduct.context).load(productModel.photo).into(binding.ivProduct)
        itemView.setOnClickListener {onClickListener(productModel)}
    }
}