package com.pops.z_gaming.rv_adapter.purchaseHistory

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pops.z_gaming.R
import com.pops.z_gaming.rv_adapter.orderDetails.ImageViewHolder

class PurchaseHistoryAdapter(
    private val numOrderList: List<String>,
    private val dateHourList: List<String>,
    private val purchaseMonthYearList: List<String>
) :

    RecyclerView.Adapter<PurchaseHistoryViewHolder>() {

    val processedMonthYears = mutableSetOf<String>() //Procesar valores de mes y año

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseHistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        //El layout del recyclerview
        return PurchaseHistoryViewHolder(
            layoutInflater.inflate(
                R.layout.purchase_items,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return numOrderList.size
    }

    override fun onBindViewHolder(holder: PurchaseHistoryViewHolder, position: Int) {

        val numOrder = numOrderList[position]
        val dateHour = dateHourList[position]
        val monthYear = purchaseMonthYearList[position]

        val isFirstOccurrence = processedMonthYears.add(monthYear) // Agrega mes y año a set, si ya existían devuelve FALSE si es nuevo TRUE
        holder.render(numOrder, dateHour, monthYear, isFirstOccurrence)
    }
}