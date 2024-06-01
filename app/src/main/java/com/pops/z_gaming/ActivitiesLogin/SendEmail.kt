package com.pops.z_gaming.ActivitiesLogin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pops.z_gaming.ActivitiesLogin.CheckEmail
import com.pops.z_gaming.databinding.FragmentSendEmailBinding

class SendEmail : AppCompatActivity() {

    private lateinit var binding: FragmentSendEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSendEmailBinding.inflate(layoutInflater)

        setContentView(binding.root)
        addListeners()
    }

    fun addListeners(){

        binding.btnReturnLogin.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        binding.btnSend.setOnClickListener {
            val intent = Intent(this, RestorePassword::class.java)
            startActivity(intent)
        }

        binding.txtReturnLogin.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}