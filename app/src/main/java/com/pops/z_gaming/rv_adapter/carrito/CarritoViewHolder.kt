package com.pops.z_gaming.rv_adapter.favorites

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pops.z_gaming.Model.FavoriteProduct
import com.pops.z_gaming.Model.Products
import com.pops.z_gaming.databinding.CarritoItemsBinding
import com.pops.z_gaming.databinding.FavoriteItemsBinding
class CarritoViewHolder(view: View) : RecyclerView.ViewHolder(view)  {

    val binding = CarritoItemsBinding.bind(view)


    fun render(favoriteProduct: FavoriteProduct, onClickListener: (FavoriteProduct) -> Unit, onDeleteClickListener: (Int, FavoriteProduct) -> Unit) {
        val producto = favoriteProduct.producto

        // Imprimir el objeto FavoriteProduct para verificar si está llegando nulo
        Log.d("Render Info", "Shopping: $favoriteProduct")

        if (producto != null) {
            binding.tvModel.text = producto.nombreProducto
            binding.tvName.text = producto.descripcion
            binding.tvPrice.text = producto.precio

            // Imprimir la URL de la imagen para verificar si es nula o vacía
            val imageUrl = producto.imagenProducto
            Log.d("Render Info", "Image URL: $imageUrl")

            // Verificar si la URL de la imagen no es nula ni vacía
            if (!imageUrl.isNullOrEmpty()) {
                Glide.with(binding.ivProduct.context)
                    .load(imageUrl)
                    .into(binding.ivProduct)
            } else {
                // Manejar el caso de URL de imagen nula o vacía
                Log.e("Glide Error", "URL de imagen nula o vacía")
            }
        } else {
            // Manejar el caso de producto nulo
            Log.e("Render Error", "El producto es nulo")
        }

        // Establecer el listener de clic en el elemento de la lista
        itemView.setOnClickListener { onClickListener(favoriteProduct) }

        // Establecer el listener de clic en el botón de eliminar
        binding.editButton.setOnClickListener { onDeleteClickListener(adapterPosition, favoriteProduct) }
    }
}