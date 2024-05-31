package com.pops.z_gaming.ActivitiesLogin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pops.z_gaming.databinding.CheckEmailBinding

class CheckEmail: AppCompatActivity() {
    private lateinit var binding: CheckEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CheckEmailBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}