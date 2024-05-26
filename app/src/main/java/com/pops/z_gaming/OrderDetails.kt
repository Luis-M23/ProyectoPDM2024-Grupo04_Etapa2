package com.pops.z_gaming

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.pops.z_gaming.databinding.FragmentFavoritesBinding
import com.pops.z_gaming.databinding.FragmentOrderDetailsBinding
import com.pops.z_gaming.rv_adapter.orderDetails.ImageAdapter
import com.pops.z_gaming.rv_adapter.orderDetails.ProductAdapter

class OrderDetails : AppCompatActivity() {

    private lateinit var binding: FragmentOrderDetailsBinding
    private lateinit var orderDetailsImageList: MutableList<String>
    private lateinit var orderDetailsNameList: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentOrderDetailsBinding.inflate(layoutInflater)
        orderDetailsImageList = mutableListOf()
        orderDetailsNameList = mutableListOf()

        setContentView(binding.root)
        initRecyclerViewImages()
        initRecyclerViewProductName()
    }

    fun initRecyclerViewImages(){

        binding.rvOrderImages.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.rvOrderImages.setHasFixedSize(true)
        for(product in ProductProvider.productList){
            orderDetailsImageList.add(product.photo)
        }
        binding.rvOrderImages.adapter = ImageAdapter(orderDetailsImageList)
    }

    fun initRecyclerViewProductName(){

        for(product in ProductProvider.productList){
            orderDetailsNameList.add(product.name)
        }

        binding.rvProductsName.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvOrderImages.setHasFixedSize(true)

        binding.rvProductsName.adapter = ProductAdapter(orderDetailsNameList)
    }
}