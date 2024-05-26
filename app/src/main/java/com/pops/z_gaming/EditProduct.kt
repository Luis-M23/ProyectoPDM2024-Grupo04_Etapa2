package com.pops.z_gaming

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.pops.z_gaming.databinding.AdminEditProductBinding
import com.pops.z_gaming.databinding.FragmentOrderDetailsBinding
import com.pops.z_gaming.rv_adapter.editProduct.EditProductAdapter
import com.pops.z_gaming.rv_adapter.orderDetails.ImageAdapter

class EditProduct : AppCompatActivity() {
    private lateinit var binding: AdminEditProductBinding
    private lateinit var orderDetailsImageList: MutableList<String>

    /**
     * Código del producto
     */
    companion object{
        const val PRODUCT_CODE = "120500Z"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminEditProductBinding.inflate(layoutInflater)
        orderDetailsImageList = mutableListOf()

        setContentView(binding.root)
        initRecyclerViewImages()
        initComponents(ProductProvider.productList[0])
    }

    fun initRecyclerViewImages() {
        binding.rvProductImages.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.rvProductImages.setHasFixedSize(true)
        var count = 0

        for(product in ProductProvider.productList){
            if(count < 3){ //Agregar 3 imagenes de prueba
                orderDetailsImageList.add(product.photo)
            }
            count++
        }

        binding.rvProductImages.adapter = EditProductAdapter(orderDetailsImageList)
        
        PagerSnapHelper().attachToRecyclerView(binding.rvProductImages)
    }

    fun initComponents(product: Products){
        binding.txtNewProductName.setText(product.name)
        binding.txtNewProductPrice.setText(product.price)
        binding.txtNewProductDescription.setText(product.model)
        binding.txtNewProductStock.setText("5")
    }
}