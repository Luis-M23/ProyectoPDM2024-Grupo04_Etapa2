package com.pops.z_gaming.rv_adapter.productAdmin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pops.z_gaming.Model.InsertProduct
import com.pops.z_gaming.Producto
import com.pops.z_gaming.R
import com.pops.z_gaming.rv_adapter.product.ProductViewHolder

class ProductAdminAdapter(
   // private val productsList: List<Producto>,
    private val context: Context,
    private val onClickListener:(Producto)->Unit,
    private val onDeleteClickListener:(idProducto: Int, idPositionInRecycler: Int) -> Unit,
    private val onUpdateClickListener:(product: InsertProduct, idProduct:Int) -> Unit
) : RecyclerView.Adapter<ProductAdminViewHolder>() {

    private var productsList: List<Producto> = listOf()
    private var productsListFull: List<Producto> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdminViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        return ProductAdminViewHolder(layoutInflater.inflate(R.layout.item_admin_home, parent, false))
    }

    //devuelve el tamanho del listado entero
    override fun getItemCount(): Int = productsList.size

    override fun onBindViewHolder(holder: ProductAdminViewHolder, position: Int) {
        val item=productsList[position]
        holder.render(item,onClickListener, onDeleteClickListener, onUpdateClickListener)
    }
    fun setProducts(products: List<Producto>) {
        this.productsList = products
        this.productsListFull = ArrayList(products)
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        val filteredList = if (query.isEmpty()) {
            productsListFull
        } else {
            productsListFull.filter {
                it.nombreProducto.contains(query, ignoreCase = true) ||
                        it.descripcion.contains(query, ignoreCase = true)
            }
        }
        productsList = filteredList
        notifyDataSetChanged()
    }
}
