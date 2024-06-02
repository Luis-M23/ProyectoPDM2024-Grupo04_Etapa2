package com.pops.z_gaming

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.pops.z_gaming.Model.FavoriteRequest
import com.pops.z_gaming.Model.ProductProvider
import com.pops.z_gaming.Model.Products
import com.pops.z_gaming.databinding.FragmentHomeBinding
import com.pops.z_gaming.rv_adapter.product.ProductAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchProducts()

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
                val retrofit= RetrofitClient.getRetrofit()
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
                val productos = retrofit.create(WebService::class.java).obtenerProductosPorCategoria(idCategoria)
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

    private fun initRecyclerView(producto:List<Producto>) {
        //val manager=GridLayoutManager(this,2)//para mostrar mas de dos items
        val manager = LinearLayoutManager(requireContext())
        binding.rvProduct.layoutManager = manager
        binding.rvProduct.adapter = ProductAdapter(
            producto,
            requireContext(),
            { producto -> onItemSelected(producto) },
            { id -> onAddToFavorite(id) }
        )
    }

    private fun onAddToFavorite(id: Int) {
        Log.i("Estas en favorito", "mensaje")

        val user = SessionManager.getUser()
        if (user != null) {
            val favoriteRequest = FavoriteRequest(idUsuario = user.idUsuario, idProducto = id, idUsuarioProducto = id)

            // Usamos Retrofit para hacer la llamada a la API
            val retrofit = RetrofitClient.getRetrofit()
            val webService = retrofit.create(WebService::class.java)

            // Realizamos la llamada en un CoroutineScope para manejo de corrutinas
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = webService.addToFavorites(favoriteRequest)
                    if (response.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(), "Added to favorites", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(), "Failed to add to favorites", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onItemSelected(producto: Producto) {
        Toast.makeText(requireContext(),producto.nombreProducto, Toast.LENGTH_SHORT).show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}