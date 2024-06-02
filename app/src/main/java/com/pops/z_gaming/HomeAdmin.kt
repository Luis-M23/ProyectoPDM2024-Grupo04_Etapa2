package com.pops.z_gaming

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.pops.z_gaming.ActivitiesLogin.Login
import com.pops.z_gaming.databinding.FragmentHomeAdminBinding
import com.pops.z_gaming.databinding.FragmentHomeBinding
import com.pops.z_gaming.rv_adapter.product.ProductAdapter
import com.pops.z_gaming.rv_adapter.productAdmin.ProductAdminAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeAdmin.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeAdmin : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentHomeAdminBinding? = null
    private val binding get() = _binding!!
    private lateinit var retrofit: Retrofit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddProduct.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Agregar un producto",
                Toast.LENGTH_LONG
            ).show()
            val intent = Intent(requireContext(), AddProduct::class.java)
            startActivity(intent)
        }
        binding.btn1.setOnClickListener {
            fetchProducts() // Fetch all products
        }

        binding.btn2.setOnClickListener {
            fetchFilteredProducts(2) // Assuming 1 is the ID for "Computo" category
        }

        binding.btn3.setOnClickListener {
            fetchFilteredProducts(1) // Assuming 2 is the ID for "Redes" category
        }
    }

    private fun fetchProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val retrofit = RetrofitClient.getRetrofit()
                val productos = retrofit.create(WebService::class.java).obtenerProductos()
                Log.d("Home", "Products received: $productos")
                requireActivity().runOnUiThread {
                    initRecyclerView(productos)
                    Toast.makeText(
                        requireContext(),
                        "Products received:${productos.size} ",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Log.e("Home", "Error fetching products", e)
                requireActivity().runOnUiThread {
                    Toast.makeText(
                        requireContext(),
                        "Error fetching products: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun fetchFilteredProducts(idCategoria: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val retrofit = RetrofitClient.getRetrofit()
                val productos = retrofit.create(WebService::class.java)
                    .obtenerProductosPorCategoria(idCategoria)
                withContext(Dispatchers.Main) {
                    initRecyclerView(productos)
                    Toast.makeText(
                        requireContext(),
                        "Filtered products received: ${productos.size}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Log.e("Home", "Error fetching filtered products", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Error fetching filtered products: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun initRecyclerView(producto: List<Producto>) {
        //val manager=GridLayoutManager(this,2)//para mostrar mas de dos items
        val manager = LinearLayoutManager(requireContext())
        binding.rvProduct.layoutManager = manager
        binding.rvProduct.adapter =
            ProductAdminAdapter(producto, requireContext(),
                { producto -> onItemSelected(producto) },
                { idProduct, idPositionInRecycler ->
                    onDeleteProduct(
                        idProduct,
                        idPositionInRecycler
                    )
                })
    }

    private fun showExitConfirmationDialog() {
        val dialogView = layoutInflater.inflate(R.layout.fragment_dialog_exit_confirmation, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        val titleTextView = dialogView.findViewById<TextView>(R.id.dialog_title)
        titleTextView.text = "Salir"

        val messageTextView = dialogView.findViewById<TextView>(R.id.dialog_message)
        messageTextView.text = "¿Estás seguro de que quieres salir de Z-Gamming 🤔?"

        val cancelButton = dialogView.findViewById<Button>(R.id.btn_cancel)
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        val exitButton = dialogView.findViewById<Button>(R.id.btn_exit)
        exitButton.setOnClickListener {
            val intent = Intent(requireContext(), Login::class.java)
            startActivity(intent)
        }

        dialog.show()
    }

    private fun onDeleteProduct(idProduct: Int, idPositionInRecycler: Int) {
        Log.i("LOGIN_T", "Eliminar un producto con id: $idProduct")
        Log.i("LOGIN_T", "Eliminar un recycler con id: $idPositionInRecycler")

        val dialogView = layoutInflater.inflate(R.layout.fragment_dialog_delete_product_confirmation, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        val messageTextView = dialogView.findViewById<TextView>(R.id.dialog_delete_message)
        messageTextView.text = "¿Seguro de eliminar el producto?"

        val cancelButton = dialogView.findViewById<Button>(R.id.btn_cancel)
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        val confirmedButton = dialogView.findViewById<Button>(R.id.btnConfirmed)
        confirmedButton.setOnClickListener {
            //Lógica para eliminar el producto
            CoroutineScope(Dispatchers.IO).launch {
                retrofit = RetrofitClient.getRetrofit()
                val call = retrofit.create(WebService::class.java).borrarProducto(idProduct)
                //val productDatesReturned = call.body()

                withContext(Dispatchers.Main) {
                    if (call.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Eliminado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                        //Eliminar elemento del recyclerview
                        binding.rvProduct.adapter?.notifyItemRemoved(idPositionInRecycler)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error al eliminar el producto",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        Log.i("LOGIN_T", "${call.message()}")
                    }
                }
            }
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun onItemSelected(producto: Producto) {
        Toast.makeText(requireContext(), producto.nombreProducto, Toast.LENGTH_SHORT).show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeAdmin.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeAdmin().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}