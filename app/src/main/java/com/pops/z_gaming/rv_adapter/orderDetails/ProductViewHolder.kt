package com.pops.z_gaming.rv_adapter.orderDetails

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pops.z_gaming.databinding.RecyclerViewProductNameBinding

class ProductViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
    private val binding = RecyclerViewProductNameBinding.bind(view)

    fun render(productName: String) {
        binding.txtProductName.text = productName
    }
}