package com.pops.z_gaming.rv_adapter.purchaseHistory

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pops.z_gaming.databinding.PurchaseItemsBinding
import com.pops.z_gaming.databinding.RecyclerViewOrderImagesBinding

class PurchaseHistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    //Imagen del recyclerview
    private val binding = PurchaseItemsBinding.bind(view)

    fun render(numOrder:String, dateHour: String, monthYear:String, isFirstOcurrence:Boolean){
        if(isFirstOcurrence){
            binding.txtMonthYear.text = monthYear
        }else{
            binding.relativeLayout.visibility = View.GONE
        }
        binding.txtnumOrden.text = numOrder
        binding.txtDateHour.text = dateHour
    }

}