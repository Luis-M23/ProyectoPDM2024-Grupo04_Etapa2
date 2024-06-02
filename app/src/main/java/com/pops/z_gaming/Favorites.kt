package com.pops.z_gaming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.pops.z_gaming.Model.FavoriteProduct
import com.pops.z_gaming.Model.FavoriteRequest
import com.pops.z_gaming.Model.ProductProvider
import com.pops.z_gaming.Model.Products
import com.pops.z_gaming.databinding.FragmentFavoritesBinding
import com.pops.z_gaming.rv_adapter.favorites.FavoritesAdapter
import com.pops.z_gaming.rv_adapter.product.ProductAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Favorites : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoriteProductsList: List<FavoriteProduct>
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
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

//    private fun initRecyclerView() {
//        val manager = LinearLayoutManager(requireContext())
//        binding.favorites.layoutManager = manager
//        // Aquí usamos el adaptador FavoritesAdapter para mostrar la lista de productos favoritos
//        binding.favorites.adapter =
//            FavoritesAdapter(ProductProvider.productList, requireContext()) { product ->
//                // Cuando se selecciona un producto, llamamos a la función onItemSelected
////                onItemSelected()
//            }
//    }

    private fun initRecyclerView() {
        // Configurar el LayoutManager del RecyclerView
        val manager = LinearLayoutManager(requireContext())
        binding.favorites.layoutManager = manager

        val adapter = FavoritesAdapter(favoriteProductsList, requireContext())
        {
            favoriteProduct ->
        }
        binding.favorites.adapter = adapter
    }


    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Favorites().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
