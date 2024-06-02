package com.pops.z_gaming.rv_adapter.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pops.z_gaming.Model.FavoriteProduct // Importa la clase FavoriteProduct
import com.pops.z_gaming.R

class FavoritesAdapter(
    private val favoriteProductsList: List<FavoriteProduct>,
    private val context: Context,
    private val onClickListener: (FavoriteProduct) -> Unit
) : RecyclerView.Adapter<FavoriteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        return FavoriteViewHolder(layoutInflater.inflate(R.layout.favorite_items, parent, false))
    }

    override fun getItemCount(): Int = favoriteProductsList.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = favoriteProductsList[position] // Accede al elemento de la lista de productos favoritos
        holder.render(item, onClickListener)
    }
}
