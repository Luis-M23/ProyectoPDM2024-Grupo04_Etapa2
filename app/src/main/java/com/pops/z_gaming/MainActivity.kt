package com.pops.z_gaming

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.pops.z_gaming.rv_adapter.ProductAdapter
import com.pops.z_gaming.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())
        initRecyclerView()

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.home -> replaceFragment(Home())
                R.id.fav -> replaceFragment(Favorites())
                R.id.cart -> replaceFragment(ShoppingCart())
                R.id.profile -> replaceFragment(Profile())

                else ->{

                }

            }

            true

        }


    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()

    }
    private fun initRecyclerView() {
        //val manager=GridLayoutManager(this,2)//para mostrar mas de dos items
        val manager= LinearLayoutManager(this)
        binding.rvProduct.layoutManager = manager
        binding.rvProduct.adapter =
            ProductAdapter(ProductProvider.productList, this) { model ->
                onItemSelected(
                    model
                )
            }
    }

    fun onItemSelected(products: Products) {
        Toast.makeText(this,products.model, Toast.LENGTH_SHORT).show()
    }
}
