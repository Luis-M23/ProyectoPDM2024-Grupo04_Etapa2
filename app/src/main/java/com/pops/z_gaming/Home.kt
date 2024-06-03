package com.pops.z_gaming

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
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
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        setupSearchFilter()
        fetchProducts()

        binding.btn1.setOnClickListener {
            fetchProducts() // Fetch all products
        }

        binding.btn2.setOnClickListener {
            fetchFilteredProducts(2) // Assuming 2 is the ID for "Computo" category
        }

        binding.btn3.setOnClickListener {
            fetchFilteredProducts(1) // Assuming 1 is the ID for "Redes" category
        }
    }

    private fun initRecyclerView() {
        adapter =  ProductAdapter(

            requireContext(),
            { producto -> onItemSelected(producto) },
            { id -> onAddToFavorite(id) }
        )
        binding.rvProduct.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProduct.adapter = adapter
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

    private fun setupSearchFilter() {
        binding.edFilter.addTextChangedListener { editable ->
            val query = editable?.toString() ?: ""
            adapter.filter(query)
        }

        binding.edFilter.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                true
            } else {
                false
            }
        }
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun fetchProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val retrofit= RetrofitClient.getRetrofit()
                val productos = retrofit.create(WebService::class.java).obtenerProductos()
                Log.d("Home", "Products received: $productos")
                requireActivity().runOnUiThread {
                    adapter.setProducts(productos)
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
                    adapter.setProducts(productos)
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

    private fun onItemSelected(producto: Producto) {
        Toast.makeText(requireContext(), producto.nombreProducto, Toast.LENGTH_SHORT).show()
    }

    companion object {
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