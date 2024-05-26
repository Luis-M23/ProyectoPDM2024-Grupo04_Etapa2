package com.pops.z_gaming.rv_adapter.editProduct

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.RecyclerView
import com.pops.z_gaming.R
import com.pops.z_gaming.rv_adapter.orderDetails.ImageViewHolder

class EditProductAdapter(private val imageUrls: List<String>):
RecyclerView.Adapter<EditProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return EditProductViewHolder(layoutInflater.inflate(R.layout.recycler_view_product_edit_images, parent, false))
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    override fun onBindViewHolder(holder: EditProductViewHolder, position: Int) {
        val item = imageUrls[position]
        holder.render(item)
    }
}