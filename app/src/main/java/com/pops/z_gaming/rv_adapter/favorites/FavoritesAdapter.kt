package com.pops.z_gaming.rv_adapter.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pops.z_gaming.Model.Products
import com.pops.z_gaming.R

class FavoritesAdapter(
    private val productsList: List<Products>,
    private val context: Context,
    private val onClickListener:(Products)->Unit
) : RecyclerView.Adapter<FavoriteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        return FavoriteViewHolder(layoutInflater.inflate(R.layout.favorite_items, parent, false))
    }
    override fun getItemCount(): Int = productsList.size
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item=productsList[position]
        holder.render(item,onClickListener)
    }
}