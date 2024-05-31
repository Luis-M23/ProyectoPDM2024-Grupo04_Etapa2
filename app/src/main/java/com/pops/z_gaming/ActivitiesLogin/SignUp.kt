package com.pops.z_gaming

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pops.z_gaming.databinding.FragmentLoginBinding
import com.pops.z_gaming.databinding.FragmentSignupBinding

class SignUp : AppCompatActivity() {
    private lateinit var binding: FragmentSignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSignupBinding.inflate(layoutInflater)

        setContentView(binding.root)
        addListeners()
    }

    fun addListeners(){
        binding.btnReturnLogin.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener{
            Toast.makeText(this, "Cuenta registrada con éxito", Toast.LENGTH_LONG).show()

            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}