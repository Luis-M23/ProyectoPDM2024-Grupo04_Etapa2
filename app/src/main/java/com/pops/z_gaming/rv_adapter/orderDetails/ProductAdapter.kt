package com.pops.z_gaming.rv_adapter.orderDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pops.z_gaming.R

class ProductAdapter(private val productNames: List<String>):
    RecyclerView.Adapter<ProductViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return ProductViewHolder(layoutInflater.inflate(R.layout.recycler_view_product_name, parent, false))
    }

    override fun getItemCount(): Int {
        return productNames.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        println("TAMAÑO LISTA: ${productNames.size}")
        val item = productNames[position]
        holder.render(item)
    }

}