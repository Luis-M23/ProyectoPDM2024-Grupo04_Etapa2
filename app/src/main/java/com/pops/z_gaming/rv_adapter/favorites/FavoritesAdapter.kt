package com.pops.z_gaming.rv_adapter.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pops.z_gaming.Model.FavoriteProduct
import com.pops.z_gaming.R

class FavoritesAdapter(
    private var favoriteProducts: List<FavoriteProduct>,
    private val onClickListener: (FavoriteProduct) -> Unit
) : RecyclerView.Adapter<FavoriteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FavoriteViewHolder(layoutInflater.inflate(R.layout.favorite_items, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = favoriteProducts[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = favoriteProducts.size

    fun updateData(newFavoriteProducts: List<FavoriteProduct>) {
        favoriteProducts = newFavoriteProducts
        notifyDataSetChanged()
    }
}
