package com.pops.z_gaming.ActivitiesUsers

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.pops.z_gaming.AddProduct
import com.pops.z_gaming.Home
import com.pops.z_gaming.MainActivity
import com.pops.z_gaming.MainActivityAdmin
import com.pops.z_gaming.Model.InsertProduct
import com.pops.z_gaming.Producto
import com.pops.z_gaming.SessionManager
import com.pops.z_gaming.databinding.FragmentDetailsBinding
import com.pops.z_gaming.databinding.FragmentPurchaseHistoryBinding

class ProductDetails : AppCompatActivity() {
    private lateinit var binding: FragmentDetailsBinding
    private var product: Producto? = null

    private var quantityToBuy: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        product = intent.getSerializableExtra("product") as? Producto

        if (product?.stock != null) {
            quantityToBuy = product!!.stock
        }

        Log.i("LOGIN_T", "PRODUCTDETAILS: $product")
        setContentView(binding.root)
        initComponents()
        initListeners()
    }

    fun initListeners() {
        binding.backButton.setOnClickListener {
            val intent: Intent

            if (SessionManager.getUser()?.idRol == 2) {
                intent = Intent(this, MainActivityAdmin::class.java)
            } else {
                intent = Intent(this, MainActivity::class.java)
            }
            startActivity(intent)
        }

        //Eventos que solo son alcanzables para rol User
        binding.btnAddQuantity.setOnClickListener {
            if (quantityToBuy > 0 && quantityToBuy < product?.stock!!) {
                quantityToBuy += 1
                setQuiantityToInsert((quantityToBuy).toString())
            }
        }

        binding.btnReduceQuantity.setOnClickListener {
            if (quantityToBuy > 1 && quantityToBuy <= product?.stock!!) {
                quantityToBuy -= 1
                setQuiantityToInsert((quantityToBuy).toString())
            }
        }

        /**
         * Método para agregar a carrito de compras
         */
        binding.btnAddToCar.setOnClickListener {

            //Retornar al home
            Toast.makeText(this, "Agregado a carrito de compras", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    fun initComponents() {

        //Si es admin no podrá agregarlo al carrito
        if(SessionManager.getUser()?.idRol == 2){
            binding.btnAddQuantity.visibility = View.GONE
            binding.btnReduceQuantity.visibility = View.GONE
            binding.btnAddToCar.visibility = View.GONE
            binding.txtQuantity.visibility = View.GONE
        }

        Glide.with(binding.ivProduct).load(product?.imagenProducto).into(binding.ivProduct)
        setQuiantityToInsert(product?.stock.toString())
        binding.txtProductPrice.text = "$ ${product?.precio}"
        binding.txtProductTitle.text = product?.nombreProducto.toString()
        binding.txtProductDescription.text = product?.descripcion.toString()
        binding.txtExistencesProducts.text = "${product?.stock} en disponibilidad"
    }

    @SuppressLint("SetTextI18n")
    fun setQuiantityToInsert(quantity: String) {
        binding.txtQuantity.text = quantity
    }
}