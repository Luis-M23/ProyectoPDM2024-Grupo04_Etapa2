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
import com.pops.z_gaming.rv_adapter.addProduct.AddProductAdapter
import com.pops.z_gaming.rv_adapter.orderDetails.ImageAdapter

class AddProduct: AppCompatActivity() {
    private lateinit var binding: AdminAddBinding
    private lateinit var uriImagesList: MutableList<String>
    private val MAX_IMAGES_ALLOWED = 3

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()){
        uriList ->

        if(uriList.isNotEmpty()){
            //Al menos una imagen seleccionada
            if(uriList.size == MAX_IMAGES_ALLOWED){
                //Tres imágenes seleccionadas
                for(uri in uriList){
                    //Enviar la imagen
                    var uriImage:String = uri.toString()
                    uriImagesList.add(uriImage)
                }
                //Log.i("TEST", "Se han seleccionado ${uriList.size} imágenes")

                //Pintar recyclerview
                showRecyclerView()
                initRecyclerView()
            }else if(uriList.size > MAX_IMAGES_ALLOWED){
                Toast.makeText(this, "El máximo de imágenes permitida son tres", Toast.LENGTH_SHORT).show()
            } else{
                Log.i("TEST", "Se han seleccionado ${uriList.size} imágenes (diferente a 3)")

                Toast.makeText(this, "Debes seleccionar tres imágenes", Toast.LENGTH_SHORT).show()
            }

        }else{
            //Imagen no seleccionada
            Toast.makeText(this, "Ninguna imagen seleccionada", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminAddBinding.inflate(layoutInflater)
        uriImagesList = mutableListOf()

        setContentView(binding.root)
        selectImage()
    }
    fun initRecyclerView(){
        binding.rvProductImages.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.rvProductImages.setHasFixedSize(true)

        if(uriImagesList.isEmpty()){
            binding.rvProductImages.adapter = AddProductAdapter(arrayListOf(""))
        }else{
            binding.rvProductImages.adapter = AddProductAdapter(uriImagesList)
        }
    }
    fun selectImage(){
        binding.ivImage.setOnClickListener {
            //Log.i("TEST", "Clickeaste")
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    fun showRecyclerView(){
        binding.rvProductImages.visibility = View.VISIBLE
        binding.ivImage.visibility = View.GONE
    }

    fun showImageViewProduct(){
        binding.rvProductImages.visibility = View.GONE
        binding.ivImage.visibility = View.VISIBLE
    }
}