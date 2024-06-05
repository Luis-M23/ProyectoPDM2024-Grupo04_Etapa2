package com.pops.z_gaming.rv_adapter.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pops.z_gaming.Model.FavoriteProduct
import com.pops.z_gaming.Model.Products
import com.pops.z_gaming.R

class CarritoAdapter(
    private var favoriteProducts: MutableList<FavoriteProduct>,
    private val onClickListener: (FavoriteProduct) -> Unit,
    private val onDeleteClickListener: ((Int, FavoriteProduct) -> Unit)? = null
) : RecyclerView.Adapter<CarritoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CarritoViewHolder(layoutInflater.inflate(R.layout.carrito_items, parent, false))
    }

    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {
        val item = favoriteProducts[position]
        holder.render(item, onClickListener, onDeleteClickListener ?: { _, _ -> })
    }

    override fun getItemCount(): Int = favoriteProducts.size

    fun updateData(newFavoriteProducts: MutableList<FavoriteProduct>) {
        favoriteProducts = newFavoriteProducts
        notifyDataSetChanged()
    }
    fun removeItem(position: Int) {
        favoriteProducts.removeAt(position)
        notifyItemRemoved(position)
    }
}