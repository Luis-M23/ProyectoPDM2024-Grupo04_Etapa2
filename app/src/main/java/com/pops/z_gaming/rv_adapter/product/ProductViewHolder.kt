package com.pops.z_gaming.rv_adapter.product

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pops.z_gaming.Producto
import com.pops.z_gaming.R
import com.pops.z_gaming.SessionManager
import com.pops.z_gaming.databinding.ItemProductsBinding

class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemProductsBinding.bind(view)
    private var isFavorito: Boolean = false // Variable mutable para manejar el estado de favorito

    fun render(
        productModel: Producto,
        onClickListener: (Producto) -> Unit,
        addOnFavoriteClickListener: (id: Int) -> Unit
    ) {
        binding.tvModel.text = productModel.nombreProducto
        binding.tvName.text = productModel.descripcion
        binding.tvPrice.text = "$${productModel.precio}"
        Glide.with(binding.ivProduct.context).load(productModel.imagenProducto).into(binding.ivProduct)
        itemView.setOnClickListener { onClickListener(productModel) }

        // Inicializar isFavorito y el icono del botón de favorito
        isFavorito = productModel.isFavorito
        binding.favButton.setImageResource(
            if (isFavorito) R.drawable.icon2_fav else R.drawable.favorite_icon
        )

        // Obtener el ID del usuario
        val idUser = SessionManager.getUser()?.idUsuario

        // Configurar el click del botón de favorito
        binding.favButton.setOnClickListener {
            if (idUser != null) {
                val productId = productModel.idProducto
                Log.i("FAVORITE", "User ID: $idUser, Product ID: $productId")

                // Cambiar el estado del icono de favorito en la UI
                isFavorito = !isFavorito
                binding.favButton.setImageResource(
                    if (isFavorito) R.drawable.icon2_fav else R.drawable.favorite_icon
                )

                // Llamar al método para actualizar el estado del favorito en la base de datos
                addOnFavoriteClickListener(productId)
            } else {
                Log.e("FAVORITE", "User not logged in")
            }
        }
    }
}
