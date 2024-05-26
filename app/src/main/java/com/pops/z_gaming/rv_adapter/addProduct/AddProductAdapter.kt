package com.pops.z_gaming.rv_adapter.addProduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pops.z_gaming.R
import com.pops.z_gaming.rv_adapter.editProduct.EditProductViewHolder

class AddProductAdapter(private val imageUrls: List<String>):
    RecyclerView.Adapter<AddProductViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return AddProductViewHolder(layoutInflater.inflate(R.layout.recycler_view_add_product_images, parent, false))
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    override fun onBindViewHolder(holder: AddProductViewHolder, position: Int) {
        val item = imageUrls[position]
        holder.render(item)
    }
}