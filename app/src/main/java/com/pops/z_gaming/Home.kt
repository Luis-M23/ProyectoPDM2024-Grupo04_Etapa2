package com.pops.z_gaming

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.pops.z_gaming.Model.ProductProvider
import com.pops.z_gaming.Model.Products
import com.pops.z_gaming.databinding.FragmentHomeBinding
import com.pops.z_gaming.rv_adapter.product.ProductAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    }
    private fun fetchProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val productos = RetrofitClient.webService.obtenerProductos()
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

    private fun initRecyclerView(producto:List<Producto>) {
        //val manager=GridLayoutManager(this,2)//para mostrar mas de dos items
        val manager= LinearLayoutManager(requireContext())
        binding.rvProduct.layoutManager = manager
        binding.rvProduct.adapter =
            ProductAdapter(producto, requireContext()) { producto ->
                onItemSelected(
                    producto
                )
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