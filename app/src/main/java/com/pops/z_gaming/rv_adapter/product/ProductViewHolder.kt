package com.pops.z_gaming.rv_adapter.product

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.View
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pops.z_gaming.MainActivity

import com.pops.z_gaming.Model.Products
import com.pops.z_gaming.Producto
import com.pops.z_gaming.SessionManager
import com.pops.z_gaming.R
import com.pops.z_gaming.databinding.ItemProductsBinding

class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemProductsBinding.bind(view)
    private var isFavorito: Boolean = false // Variable mutable para manejar el estado de favorito


    fun render(  productModel: Producto,
                 onClickListener: (Producto) -> Unit,
                 addOnFavoriteClickListener: (id: Int) -> Unit,
                 addOnShoppingCartClickListener: (Producto) -> Unit) {
        binding.tvModel.text = productModel.nombreProducto
        binding.tvName.text = productModel.descripcion
        binding.tvPrice.text = "$${productModel.precio}"
        Glide.with(binding.ivProduct.context).load(productModel.imagenProducto).into(binding.ivProduct)
        itemView.setOnClickListener {onClickListener(productModel)}

        // Inicializar isFavorito y el icono del botón de favorito
        isFavorito = productModel.isFavorito

        binding.editButton.setImageResource(
            if (isFavorito) R.drawable.baseline_favorite_24 else R.drawable.favorite_icon
        )
        val idUser = SessionManager.getUser()?.idUsuario
        binding.editButton.setOnClickListener {
            if (idUser != null) {
                val productId = productModel.idProducto
                Log.i("FAVORITE", "User ID: $idUser, Product ID: $productId")
                //isFavorito = !isFavorito

                //Enviar si al hacer click se quita el favorito o no

                if(isFavorito ){
                    //Si da click y es favorito, cambia la imagen
                    //binding.editButton.setImageResource(R.drawable.favorite_icon)
                    Log.i("LOGIN_T", "Favorito a No favorito (ELIMINANDO)")
                    Toast.makeText(binding.editButton.context, "Ve a la vista de favoritos para quitarlo.", Toast.LENGTH_SHORT).show()
                }else{
                    //si da click y no es favorito pone
                    binding.editButton.setImageResource( R.drawable.baseline_favorite_24)
                    Log.i("LOGIN_T", "No fvorito a favorito (AGREGANDO)")
                    addOnFavoriteClickListener(productId)
                }
            } else {
                Log.e("FAVORITE", "User not logged in")
            }
        }

        //Ir al carrito de compras
        binding.deleteButton.setOnClickListener{
            addOnShoppingCartClickListener(productModel)
        }
    }
}