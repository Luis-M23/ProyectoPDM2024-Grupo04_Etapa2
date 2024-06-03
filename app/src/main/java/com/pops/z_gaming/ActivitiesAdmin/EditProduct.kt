package com.pops.z_gaming

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.pops.z_gaming.Model.InsertProduct
import com.pops.z_gaming.Model.ProductProvider
import com.pops.z_gaming.Model.Products
import com.pops.z_gaming.databinding.AdminEditProductBinding
import com.pops.z_gaming.rv_adapter.editProduct.EditProductAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class EditProduct : AppCompatActivity() {
    private lateinit var binding: AdminEditProductBinding
    private var product: InsertProduct? = null
    private var idProduct: Int = -1


    private var productImageUrl: String = ""
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminEditProductBinding.inflate(layoutInflater)
        retrofit = RetrofitClient.getRetrofit()

        // Recuperar las variables del Intent
        product = intent.getSerializableExtra("product") as? InsertProduct
        idProduct = intent.getIntExtra("id", -1)

        // Usa newProduct y idProduct
        Log.i("LOGIN_T", "Editproduct, product $product")
        Log.i("LOGIN_T", "Editproduct, idProduct $idProduct")

        setContentView(binding.root)

        initSpinner()
        initComponents()
        initListeners()
    }
    fun returnToMainAdmin() {
        clearAllEditText()
        val intent = Intent(applicationContext, MainActivityAdmin::class.java)
        startActivity(intent)
    }

    private fun clearAllEditText() {
        binding.txtProductUrl.text?.clear()
        binding.ivPreview.visibility = View.GONE
        binding.txtProductName.text?.clear()
        binding.txtProductDescription.text?.clear()
        binding.txtProductPrice.text?.clear()
        binding.txtProductStock.text?.clear()
        binding.productCategorySpinner.setSelection(0) // Opcional: Reiniciar el spinner a la primera posición
    }

    fun initListeners() {
        binding.btnReturnLogin.setOnClickListener{
            returnToMainAdmin()
        }
        binding.btnCancelEdit.setOnClickListener{
            returnToMainAdmin()
        }
        binding.btnSaveChangesEdit.setOnClickListener {
            updateProduct()
        }
        binding.txtProductUrl.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                showImagePreview()
            }
        }
    }

    private fun updateProduct() {
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

                var productToUpdate = InsertProduct(
                    productName,
                    productDescription,
                    productPrice.toDouble(),
                    productStock.toInt(),
                    productImageUrl,
                    false,
                    categoryId.toInt(),
                    false
                )
                Log.i("LOGIN_T", "EDDITPRODUCT, OBJECT TO INSERT: ${productToUpdate}")

                CoroutineScope(Dispatchers.IO).launch {

                    val call =
                        retrofit.create(WebService::class.java).actualizarProducto(idProduct, productToUpdate)
                    val productReturned = call.body()

                    Log.i("LOGIN_T", "EDITPRODUCT, response: ${productReturned}")

                    withContext(Dispatchers.Main) {
                        if (call.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "Producto actualizado satisfactoriamente",
                                Toast.LENGTH_SHORT
                            ).show()

                            returnToMainAdmin()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Error al actualizar el producto ${call.message()}",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.i("LOGIN_T", "ADDPRODUCT, ERROr: ${call.message()}")
                        }
                    }
                }
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

    fun initComponents(){
        binding.txtProductUrl.setText(product?.imagenProducto)
        Glide.with(binding.ivPreview).load(product?.imagenProducto).into(binding.ivPreview)
        binding.txtProductName.setText(product?.nombreProducto)
        binding.txtProductDescription.setText(product?.descripcion)

        var price = product?.precio.toString()
        binding.txtProductPrice.setText(price)

        binding.txtProductStock.setText(product?.stock.toString())
        product?.idCategoria?.let { binding.productCategorySpinner.setSelection(it) }
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

}