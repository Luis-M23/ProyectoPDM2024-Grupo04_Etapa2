package com.pops.z_gaming.ActivitiesUsers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pops.z_gaming.databinding.FragmentPurchaseHistoryBinding
import com.pops.z_gaming.rv_adapter.purchaseHistory.PurchaseHistoryAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class PurchaseHistory : AppCompatActivity() {
    private lateinit var binding: FragmentPurchaseHistoryBinding
    private lateinit var numOrderList: MutableList<String>
    private lateinit var dateHourList: MutableList<String>
    private lateinit var purchaseMonthYear: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentPurchaseHistoryBinding.inflate(layoutInflater)

        numOrderList = mutableListOf()
        dateHourList = mutableListOf()
        purchaseMonthYear = mutableListOf()

        setContentView(binding.root)
        initRecycerViewPurchaseDetails()
    }

    fun initRecycerViewPurchaseDetails(){
        binding.rvPurchase.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvPurchase.setHasFixedSize(true)

        val numOrderList = mutableListOf(
            "120500Z",
            "120501A",
            "120502B",
            "120503C",
            "120504D",
            "120505E",
            "120506F"
        )

        val dateHourList = mutableListOf(
            "02/04/2024, 12:17:23 pm",
            "02/04/2024, 09:30:45 am",
            "22/06/2022, 11:45:56 am",
            "22/06/2022, 01:15:12 pm",
            "22/06/2022, 03:00:34 pm",
            "19/09/2019, 04:20:22 pm",
            "28/10/2018, 06:10:45 pm"
        )

        //Sacar de la lista de horas de string su respectivo texto
        for(dateHour in dateHourList){
            val date = convertToDate(dateHour)
            val year = getYear(date)
            val month = getMonth(date)

            println("$month $year")
            purchaseMonthYear.add("$month $year")

        }

        binding.rvPurchase.adapter = PurchaseHistoryAdapter(numOrderList, dateHourList, purchaseMonthYear)
    }


    /**
     * Método para convertir string en un formato de fecha
     */
    fun convertToDate(dateHour: String): Date?{
        val dateFormat = SimpleDateFormat("dd/MM/yyyy, hh:mm:ss a", Locale.getDefault())
        return try {
            dateFormat.parse(dateHour)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getMonth(date:Date?):String?{
        val calendar = Calendar.getInstance()
        if (date != null) {
            calendar.time = date
        }
        val monthFormat = SimpleDateFormat("MMMM", Locale("es", "ES"))
        return monthFormat.format(calendar.time)
    }

    fun getYear(date:Date?):Int?{
        val calendar = Calendar.getInstance()
        if (date != null) {
            calendar.time = date
        }
        return calendar.get(Calendar.YEAR)
    }
}