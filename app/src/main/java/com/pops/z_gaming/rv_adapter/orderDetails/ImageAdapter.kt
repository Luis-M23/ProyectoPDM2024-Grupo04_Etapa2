package com.pops.z_gaming.rv_adapter.orderDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.pops.z_gaming.R

class ImageAdapter(private val imageUrls: List<String>):
    RecyclerView.Adapter<ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return ImageViewHolder(layoutInflater.inflate(R.layout.recycler_view_order_images, parent, false))
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = imageUrls[position]
        holder.render(item)
    }
}
