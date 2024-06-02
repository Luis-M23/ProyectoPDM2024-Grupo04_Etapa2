package com.pops.z_gaming.rv_adapter.productAdmin

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pops.z_gaming.Producto
import com.pops.z_gaming.databinding.ItemAdminHomeBinding
import com.pops.z_gaming.databinding.ItemProductsBinding

class ProductAdminViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemAdminHomeBinding.bind(view)


    fun render(productModel: Producto, onClickListener:(Producto)->Unit) {
        binding.tvModel.text = productModel.nombreProducto
        binding.tvName.text = productModel.descripcion
        binding.tvPrice.text = "$${productModel.precio}"
        Glide.with(binding.ivProduct.context).load(productModel.imagenProducto).into(binding.ivProduct)
        itemView.setOnClickListener {onClickListener(productModel)}
    }

}