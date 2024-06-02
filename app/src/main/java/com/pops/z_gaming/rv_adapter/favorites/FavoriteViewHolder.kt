package com.pops.z_gaming.rv_adapter.favorites

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pops.z_gaming.Model.FavoriteProduct
import com.pops.z_gaming.Model.Products
import com.pops.z_gaming.Model.UsuarioProducto
import com.pops.z_gaming.databinding.FavoriteItemsBinding
class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view)  {

    val binding = FavoriteItemsBinding.bind(view)

    fun render(favoriteProduct: FavoriteProduct, onClickListener: (FavoriteProduct) -> Unit) {
        binding.tvModel.text = favoriteProduct.name
        binding.tvName.text = favoriteProduct.description
        binding.tvPrice.text = favoriteProduct.price.toString() // Convierte el precio a String
        // Carga la imagen utilizando Glide
        Glide.with(binding.ivProduct.context).load(favoriteProduct.photo).into(binding.ivProduct)
        // Establece un listener de clic en el elemento de la lista
        itemView.setOnClickListener { onClickListener(favoriteProduct) }
    }
}