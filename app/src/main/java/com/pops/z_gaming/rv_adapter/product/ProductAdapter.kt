package com.pops.z_gaming.rv_adapter.product

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pops.z_gaming.Producto
import com.pops.z_gaming.R

class ProductAdapter(
    private val productsList: List<Producto>,
    private val context: Context,
    private val onClickListener:(Producto)->Unit,
    private val onAddToCartClickListener: (Int) -> Unit
) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        return ProductViewHolder(layoutInflater.inflate(R.layout.item_products, parent, false))
    }

    //devuelve el tamanho del listado entero
    override fun getItemCount(): Int = productsList.size


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item=productsList[position]
        holder.render(item,onClickListener, onAddToCartClickListener)
    }
}
