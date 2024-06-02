package com.pops.z_gaming.rv_adapter.product

import android.view.View

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.pops.z_gaming.Producto
import com.pops.z_gaming.databinding.ItemProductsBinding

class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemProductsBinding.bind(view)


    fun render(
        productModel: Producto,
        onClickListener: (Producto) -> Unit,
        onAddToCartClickListener: (Int) -> Unit
    ) {
        binding.tvModel.text = productModel.nombreProducto
        binding.tvName.text = productModel.descripcion
        binding.tvPrice.text = "$${productModel.precio}"
        Glide.with(binding.ivProduct.context).load(productModel.imagenProducto).into(binding.ivProduct)
        itemView.setOnClickListener {onClickListener(productModel)

        }
            // Manejar clic en el botón del carrito
            binding.carritoButton.setOnClickListener {
                onAddToCartClickListener(productModel.idProducto)
            }

            // Manejar clic en el elemento completo
            itemView.setOnClickListener {
                onClickListener(productModel)

        }
    }
}