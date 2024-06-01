package com.pops.z_gaming

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.pops.z_gaming.databinding.AdminAddBinding
import com.pops.z_gaming.databinding.AdminEditProductBinding
import com.pops.z_gaming.rv_adapter.orderDetails.ImageAdapter

class AddProduct: AppCompatActivity() {
    private lateinit var binding: AdminAddBinding
    private lateinit var uriImagesList: MutableList<String>
    private val MAX_IMAGES_ALLOWED = 3

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){
        uri ->
        if(uri!=null) {
            Log.i("LOGIN_T", "AdminAddProduct, URI: $uri")
            binding.ivImage.setImageURI(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminAddBinding.inflate(layoutInflater)
        uriImagesList = mutableListOf()

        setContentView(binding.root)
        selectImage()
    }
    fun selectImage(){
        binding.ivImage.setOnClickListener {
            //Log.i("TEST", "Clickeaste")
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }
}