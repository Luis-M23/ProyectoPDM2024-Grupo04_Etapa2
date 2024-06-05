package com.pops.z_gaming

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.pops.z_gaming.Model.FavoriteProduct
import com.pops.z_gaming.Model.ProductProvider
import com.pops.z_gaming.Model.Products
import com.pops.z_gaming.Model.UpdateIsFavoriteProduct
import com.pops.z_gaming.databinding.FragmentFavoritesBinding
import com.pops.z_gaming.databinding.FragmentShoppingCartBinding
import com.pops.z_gaming.rv_adapter.favorites.CarritoAdapter
import com.pops.z_gaming.rv_adapter.favorites.FavoritesAdapter
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
 * Use the [ShoppingCart.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShoppingCart : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentShoppingCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var carritoAdapter: CarritoAdapter
    //    private var favoriteProductsList: List<FavoriteProduct> = mutableListOf()
    private var carritoProductsList: MutableList<FavoriteProduct> = mutableListOf()

    private val apiService: WebService by lazy {
        RetrofitClient.getRetrofit().create(WebService::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentShoppingCartBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        loadFavoriteProducts()
    }
    private fun initRecyclerView() {

        carritoAdapter = CarritoAdapter(
            carritoProductsList,
            onClickListener = { favoriteProduct ->
                // Handle click event here
            },
            onDeleteClickListener = { position, favoriteProduct ->
                deleteFavoriteProduct(position, favoriteProduct)
            }
        )
        binding.favorites.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = carritoAdapter
        }
    }

    private fun onItemSelected(products: Products) {
        Toast.makeText(requireContext(),products.model, Toast.LENGTH_SHORT).show()
    }

    private fun loadFavoriteProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            val idUsuario: Int = SessionManager.getUser()?.idUsuario ?: 0
            if (idUsuario != 0) {
                try {
                    val response = apiService.obtenerShoppingCartItems(idUsuario.toLong())
                    if (response.isSuccessful) {
                        val newCartItemsProductsList = response.body()?.toMutableList() ?: mutableListOf()
                        withContext(Dispatchers.Main) {
                            carritoProductsList.clear()
                            carritoProductsList.addAll(newCartItemsProductsList)
                            carritoAdapter.updateData(carritoProductsList)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            // Handle error here
                            Log.i("API Error", "Failed to load cart items products: ${response.message()}")
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        // Handle error here
                        Log.i("API Error", "Failed to load cart items products: ${e.message}")
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    Log.i("API Error", "User ID is not available")
                }
            }
        }
    }
    private fun deleteFavoriteProduct(position: Int, favoriteProduct: FavoriteProduct) {
        val idUsuarioProducto = favoriteProduct.idUsuarioProducto.toInt() // Convertir a Int
        val idProducto = favoriteProduct.idProducto?.toInt()
        Log.d("Delete Cart Item", "ID Usuario Producto: $idUsuarioProducto")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.removeFromFavorites(idUsuarioProducto)
                if (response.isSuccessful) {
                    // Eliminación del servidor exitosa

                    withContext(Dispatchers.Main) {
                        // Eliminar localmente del RecyclerView
                        carritoAdapter.removeItem(position)
                    }


                    //Actualizar estado del producto
                    if(idProducto != null){
                        Log.i("Delete Product", "UPDATING isAddedInCart for product: ${idProducto}")

                        val response = apiService.actualizarEstadoDeFavoritoProducto(idProducto, UpdateIsFavoriteProduct(false))

                        if(response.isSuccessful){
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    requireContext(),
                                    "Producto eliminado de shopping cart",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }else{
                            Log.i("Delete Product", "Error UPDATE isAddedInCart: ${response.message()}")
                        }
                    }

                } else {
                    withContext(Dispatchers.Main) {
                        // Handle error here
                        Log.e(
                            "API Error",
                            "Failed to remove shopping items product: ${response.message()}"
                        )
                        Toast.makeText(
                            requireContext(),
                            "Error al eliminar el producto del shopping cart",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    // Handle error here
                    Log.e("API Error", "EXCEPTION Failed to remove cart items product: ${e.message}")
                    Toast.makeText(
                        requireContext(),
                        "Error al eliminar el carrito",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShoppingCart.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShoppingCart().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}