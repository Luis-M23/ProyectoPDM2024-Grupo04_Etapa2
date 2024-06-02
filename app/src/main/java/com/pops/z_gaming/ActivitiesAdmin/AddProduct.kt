package com.pops.z_gaming

import android.R
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.pops.z_gaming.Model.InsertProduct
import com.pops.z_gaming.Model.UserLogin
import com.pops.z_gaming.databinding.AdminAddBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import java.io.FileNotFoundException


class AddProduct : AppCompatActivity() {
    private lateinit var binding: AdminAddBinding
    private lateinit var retrofit: Retrofit
    private var productImageUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminAddBinding.inflate(layoutInflater)
        retrofit = RetrofitClient.getRetrofit()

        setContentView(binding.root)
        initListeners()
        initSpinner()
    }

    fun initListeners() {

        binding.btnAddProduct.setOnClickListener {
            insertProduct()
        }

        binding.btnCancelAdd.setOnClickListener {
            returnToMainAdmin()
        }

        binding.btnReturnLogin.setOnClickListener {
            returnToMainAdmin()
        }
        binding.txtProductUrl.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                showImagePreview()
            }
        }
    }

    private fun showImagePreview() {
        productImageUrl = (binding.txtProductUrl.text.toString())

        Glide.with(binding.ivPreview).load(productImageUrl)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.txtProductUrl.error = "la URL ingresada no es válida"
                    binding.ivPreview.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    Toast.makeText(applicationContext, "La imagen es válida", Toast.LENGTH_SHORT)
                        .show()
                    binding.ivPreview.visibility = View.VISIBLE
                    return false
                }
            })
            .into(binding.ivPreview)

        Log.i("LOGIN_T", "ADDPRODUCT, URL: $productImageUrl")
    }

    fun initSpinner() {
        // Opciones del spinner
        val opciones = arrayOf("Zona gamer", "Zona computo", "Zona redes", "Zona electronica")

        // Adaptador para el spinner
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        // Asignar el adaptador al spinner
        binding.productCategorySpinner.adapter = adapter
    }

    fun insertProduct() {
        val productName = binding.txtProductName.text.toString()
        val productDescription = binding.txtProductDescription.text.toString()
        val productPrice = binding.txtProductPrice.text.toString()
        val productStock = binding.txtProductStock.text.toString()
        val categoryId = (binding.productCategorySpinner.selectedItemId + 1)

        when {
            productName.isEmpty() -> {
                binding.txtProductName.error = "Este campo es obligatorio"
                binding.txtProductName.requestFocus()
            }

            productDescription.isEmpty() -> {
                binding.txtProductDescription.error = "Este campo es obligatorio"
                binding.txtProductDescription.requestFocus()
            }

            productPrice.isEmpty() -> {
                binding.txtProductPrice.error = "Este campo es obligatorio"
                binding.txtProductPrice.requestFocus()
            }

            productStock.isEmpty() -> {
                binding.txtProductStock.error = "Este campo es obligatorio"
                binding.txtProductStock.requestFocus()
            }

            productImageUrl.isEmpty() -> {
                binding.txtProductUrl.error = "Debes agregar una URL de la imagen en linea"
                binding.txtProductUrl.requestFocus()
            }

            binding.ivPreview.visibility.equals(View.GONE) -> {
                binding.txtProductUrl.error = "Arega una URL válida"
                binding.txtProductUrl.requestFocus()
            }

            else -> {

                var productToInsert = InsertProduct(
                    productName,
                    productDescription,
                    productPrice.toDouble(),
                    productStock.toInt(),
                    productImageUrl,
                    false,
                    categoryId.toInt(),
                    false
                )
                Log.i("LOGIN_T", "ADDPRODUCT, OBJECT: ${productToInsert}")

                CoroutineScope(Dispatchers.IO).launch {

                    val call =
                        retrofit.create(WebService::class.java).agregarProducto(productToInsert)
                    val productReturned = call.body()

                    Log.i("LOGIN_T", "ADDPRODUCT, response: ${productReturned}")

                    withContext(Dispatchers.Main) {
                        if (call.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "Producto agregado satisfactoriamente",
                                Toast.LENGTH_SHORT
                            ).show()

                            returnToMainAdmin()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Error al agregar el producto ${call.message()}",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.i("LOGIN_T", "ADDPRODUCT, ERROr: ${call.message()}")
                        }
                    }
                }
            }
        }
    }

    fun returnToMainAdmin() {
        clearAllEditText()
        val intent = Intent(applicationContext, MainActivityAdmin::class.java)
        startActivity(intent)
    }

    private fun clearAllEditText() {
        binding.txtProductName.text?.clear()
        binding.txtProductDescription.text?.clear()
        binding.txtProductPrice.text?.clear()
        binding.txtProductStock.text?.clear()
        binding.productCategorySpinner.setSelection(0) // Opcional: Reiniciar el spinner a la primera posición
    }
}